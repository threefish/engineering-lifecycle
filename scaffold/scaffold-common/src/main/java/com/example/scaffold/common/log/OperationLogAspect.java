package com.example.scaffold.common.log;

import com.example.scaffold.common.context.UserContext;
import com.example.scaffold.common.util.SensitiveUtil;
import com.example.scaffold.common.util.TraceIdHolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * 操作日志切面：环绕 @OperationLog 方法计时，捕获成败与入参（脱敏），发布事件异步落库。
 * 日志机制绝不破坏主业务：发布失败仅告警。
 *
 * @author scaffold
 * @date 2026-11-19
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE + 100)
public class OperationLogAspect {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    private final ApplicationEventPublisher publisher;

    @Around("@annotation(operationLog)")
    public Object around(ProceedingJoinPoint pjp, OperationLog operationLog) throws Throwable {
        long start = System.currentTimeMillis();
        String traceId = TraceIdHolder.get();
        boolean success = true;
        String errorMessage = null;
        try {
            return pjp.proceed();
        } catch (Throwable e) {
            success = false;
            errorMessage = e.getMessage();
            throw e;
        } finally {
            OperationLogEvent event = new OperationLogEvent();
            event.setOperateType(operationLog.operateType().name());
            event.setContent(operationLog.content());
            event.setKeyword(operationLog.keyword());
            event.setMethod(pjp.getSignature().toShortString());
            event.setParams(SensitiveUtil.clean(serializeArgs(pjp.getArgs())));
            event.setOperateUser(SensitiveUtil.clean(
                    UserContext.getUserName() != null ? UserContext.getUserName() : UserContext.getUserId()));
            event.setTraceId(traceId);
            event.setCostMs(System.currentTimeMillis() - start);
            event.setSuccess(success);
            event.setErrorMessage(SensitiveUtil.clean(errorMessage));
            event.setOperateTime(LocalDateTime.now());
            try {
                publisher.publishEvent(event);
            } catch (Exception ex) {
                log.warn("操作日志事件发布失败 method={}", event.getMethod(), ex);
            }
        }
    }

    private String serializeArgs(Object[] args) {
        if (args == null || args.length == 0) {
            return "";
        }
        try {
            return MAPPER.writeValueAsString(args);
        } catch (Exception e) {
            return Arrays.toString(args);
        }
    }
}