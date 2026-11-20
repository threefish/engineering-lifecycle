package com.example.scaffold.domain.service.impl;

import com.example.scaffold.acl.client.DemoExternalClient;
import com.example.scaffold.common.config.ConfigService;
import com.example.scaffold.common.constant.AppConfigConstant;
import com.example.scaffold.common.exception.Assert;
import com.example.scaffold.common.exception.CommonException;
import com.example.scaffold.common.exception.NotFoundException;
import com.example.scaffold.common.lock.LockHelper;
import com.example.scaffold.common.page.PageResult;
import com.example.scaffold.common.transaction.AfterCommitExecutor;
import com.example.scaffold.domain.service.NoteService;
import com.example.scaffold.infrastructure.convert.NoteConvert;
import com.example.scaffold.infrastructure.repository.NoteRepository;
import com.example.scaffold.infrastructure.repository.entity.NoteEntity;
import com.example.scaffold.model.constant.BusinessConstant;
import com.example.scaffold.model.enums.NoteStatus;
import com.example.scaffold.model.note.NoteVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 备忘录业务实现：写事务 rollbackFor、读事务 readOnly；仓储继承 BaseRepository，直接用 saveOrUpdate/updateById
 * 与仓储查询方法（恒过滤 deleted=false）；汇聚配置服务、断言、转换、锁、事务后置异步副作用与防腐层客户端。
 *
 * @author scaffold
 * @date 2026-11-19
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final ConfigService configService;
    private final DemoExternalClient demoExternalClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(NoteVO.SaveRequest request) {
        int maxLen = configService.getInt(AppConfigConstant.Note.TITLE_MAX_LENGTH, 100);
        Assert.isTrue(request.getTitle().length() <= maxLen, CommonException.Note.TITLE_TOO_LONG, maxLen);

        NoteEntity entity;
        if (request.getId() == null) {
            entity = NoteConvert.INSTANCE.toEntity(request);
            if (entity.getStatus() == null) {
                entity.setStatus(NoteStatus.ENABLED);
            }
            entity.setDeleted(false);
        } else {
            entity = requireNote(request.getId());
            NoteConvert.INSTANCE.updateEntity(request, entity);
        }
        noteRepository.saveOrUpdate(entity);

        // 外部副作用必须在事务提交后执行，避免「事务回滚但外部已同步」
        AfterCommitExecutor.runAfterCommitAsync(
                "同步备忘录标题到外部系统[" + entity.getId() + "]",
                () -> demoExternalClient.syncName(String.valueOf(entity.getId()), entity.getTitle()));
        log.info("备忘录保存成功 id={}", entity.getId());
        return entity.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public NoteVO.Detail detail(NoteVO.IdRequest request) {
        return NoteConvert.INSTANCE.toDetail(requireNote(request.getId()));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResult<NoteVO.Item> pagingQueryByCondition(NoteVO.PageQuery query) {
        return noteRepository.pageByCondition(query).map(NoteConvert.INSTANCE::toItem);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void enableOrDisable(NoteVO.EnableOrDisableRequest request) {
        String lockKey = String.format(BusinessConstant.LockKey.NOTE_ENABLE_OR_DISABLE, request.getId());
        LockHelper.runWithTryLock(() -> {
            NoteEntity entity = requireNote(request.getId());
            entity.setStatus(request.getStatus());
            noteRepository.updateById(entity);
            return null;
        }, lockKey);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(NoteVO.IdRequest request) {
        NoteEntity entity = requireNote(request.getId());
        entity.setDeleted(true);
        noteRepository.updateById(entity);
        log.info("备忘录逻辑删除 id={}", request.getId());
    }

    private NoteEntity requireNote(Long id) {
        return noteRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException(CommonException.Note.NOT_EXIST, id));
    }
}