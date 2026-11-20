package com.example.scaffold.common.log;

import com.example.scaffold.common.context.UserContext;
import com.example.scaffold.common.util.TraceIdHolder;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

/**
 * 人工操作日志助手：domain 内记录关键业务动作，静态调用，异步落库；
 * operateUser 为空时自动取 {@link UserContext} 当前登录用户。
 *
 * @author scaffold
 * @date 2026-11-19
 */
@Component
public class OperationLogHelper {

    private static ApplicationEventPublisher publisher;

    public OperationLogHelper(ApplicationEventPublisher publisher) {
        OperationLogHelper.publisher = publisher;
    }

    public static void add(Long businessId, String content, String operateUser) {
        if (publisher == null) {
            return;
        }
        OperationLogEvent event = new OperationLogEvent();
        event.setOperateType(OperateType.OTHER.name());
        event.setContent(content);
        event.setBusinessId(businessId);
        event.setOperateUser(StringUtils.hasText(operateUser) ? operateUser : UserContext.getUserName());
        event.setTraceId(TraceIdHolder.get());
        event.setSuccess(true);
        event.setOperateTime(LocalDateTime.now());
        publisher.publishEvent(event);
    }
}