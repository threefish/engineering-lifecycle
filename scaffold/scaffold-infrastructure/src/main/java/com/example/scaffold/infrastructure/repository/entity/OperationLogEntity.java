package com.example.scaffold.infrastructure.repository.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.scaffold.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 操作日志实体：异步独立事务落库。
 *
 * @author scaffold
 * @date 2026-11-19
 */
@TableName("t_operation_log")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OperationLogEntity extends BaseEntity {

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

    /** 业务主键 */
    private Long businessId;

    /** 操作人 */
    private String operateUser;

    /** 追踪号 */
    private String traceId;

    /** 耗时毫秒 */
    private Long costMs;

    /** 是否成功 */
    private Boolean success;

    /** 失败原因 */
    private String errorMessage;

    /** 操作时间 */
    private LocalDateTime operateTime;
}