package com.example.scaffold.infrastructure.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.scaffold.common.page.PageResult;
import com.example.scaffold.common.page.PageUtil;
import com.example.scaffold.infrastructure.repository.entity.NoteEntity;
import com.example.scaffold.infrastructure.repository.mapper.NoteMapper;
import com.example.scaffold.model.note.NoteVO;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * 备忘录仓储（对齐 canjiajiu 写法）：单类继承 BaseRepository，查询用 lambdaQuery() 链式 API。
 * 逻辑删除手动维护：所有查询显式带 deleted=false；分页走 MP IPage 后转统一 PageResult。
 *
 * @author scaffold
 * @date 2026-11-19
 */
@Repository
public class NoteRepository extends BaseRepository<NoteMapper, NoteEntity> {

    /** 按主键取未删除记录 */
    public Optional<NoteEntity> findByIdAndDeletedFalse(Long id) {
        return lambdaQuery()
                .eq(NoteEntity::getId, id)
                .eq(NoteEntity::getDeleted, false)
                .oneOpt();
    }

    /** 按条件分页查询（恒过滤 deleted=false，id 倒序） */
    public PageResult<NoteEntity> pageByCondition(NoteVO.PageQuery query) {
        int pageNo = PageUtil.normalizePageNo(query.getPageNo());
        int pageSize = PageUtil.normalizePageSize(query.getPageSize());

        LambdaQueryChainWrapper<NoteEntity> chain = lambdaQuery().eq(NoteEntity::getDeleted, false);
        if (StringUtils.hasText(query.getTitle())) {
            // MP 的 like 不支持 ESCAPE，用参数化 apply 显式转义字面 %/_（SQL 片段为常量，值走 {0} 绑定）
            chain.apply("title LIKE {0} ESCAPE '!'", "%" + escapeLike(query.getTitle()) + "%");
        }
        if (query.getStatus() != null) {
            chain.eq(NoteEntity::getStatus, query.getStatus());
        }

        IPage<NoteEntity> page = chain.orderByDesc(NoteEntity::getId).page(Page.of(pageNo, pageSize));
        return PageUtil.toResult(page.getRecords(), page.getTotal(), pageNo, pageSize);
    }

    private static String escapeLike(String input) {
        return input.replace("!", "!!").replace("%", "!%").replace("_", "!_");
    }
}