package com.example.scaffold.common.log;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 操作日志事件：切面/助手发布，domain 监听异步落库。
 *
 * @author scaffold
 * @date 2026-11-19
 */
@Data
public class OperationLogEvent {

    /** 操作类型 */
    private String operateType;

    /** 操作描述 */
    private String content;

    /** 检索关键字 */
    private String keyword;

    /** 目标方法签名 */
    private String method;

    /** 入参（脱敏截断后） */
    private String params;

    /** 业务主键（人工日志用） */
    private Long businessId;

    /** 操作人 */
    private String operateUser;

    /** 追踪号 */
    private String traceId;

    /** 耗时毫秒 */
    private long costMs;

    /** 是否成功 */
    private boolean success;

    /** 失败原因（脱敏截断后） */
    private String errorMessage;

    /** 操作时间 */
    private LocalDateTime operateTime;
}