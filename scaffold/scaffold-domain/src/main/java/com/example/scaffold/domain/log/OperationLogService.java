package com.example.scaffold.domain.log;

import com.example.scaffold.common.log.OperationLogEvent;
import com.example.scaffold.infrastructure.repository.OperationLogRepository;
import com.example.scaffold.infrastructure.repository.entity.OperationLogEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 操作日志落库：独立事务 REQUIRES_NEW，仅在异步线程（主事务已提交）执行，避免同线程嵌套占用双连接。
 *
 * @author scaffold
 * @date 2026-11-19
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OperationLogService {

    private final OperationLogRepository operationLogRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void record(OperationLogEvent event) {
        OperationLogEntity entity = new OperationLogEntity();
        entity.setOperateType(event.getOperateType());
        entity.setContent(event.getContent());
        entity.setKeyword(event.getKeyword());
        entity.setMethod(event.getMethod());
        entity.setParams(event.getParams());
        entity.setBusinessId(event.getBusinessId());
        entity.setOperateUser(event.getOperateUser());
        entity.setTraceId(event.getTraceId());
        entity.setCostMs(event.getCostMs());
        entity.setSuccess(event.isSuccess());
        entity.setErrorMessage(event.getErrorMessage());
        entity.setOperateTime(event.getOperateTime());
        operationLogRepository.save(entity);
    }
}