package com.example.scaffold.domain.log;

import com.example.scaffold.common.concurrent.ThreadPoolHolder;
import com.example.scaffold.common.log.OperationLogEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 操作日志监听：收到事件后投递统一线程池异步落库，不阻塞主业务、不影响主事务。
 *
 * @author scaffold
 * @date 2026-11-19
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OperationLogListener {

    private final OperationLogService operationLogService;

    @EventListener
    public void onOperationLog(OperationLogEvent event) {
        ThreadPoolHolder.execute("操作日志落库", () -> operationLogService.record(event));
    }
}