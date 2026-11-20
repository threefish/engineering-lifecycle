package com.example.scaffold.common.log;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口操作日志：标注在 Controller 方法上，由切面计时并发事件，异步落库。
 *
 * @author scaffold
 * @date 2026-11-19
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationLog {

    /** 操作类型 */
    OperateType operateType() default OperateType.OTHER;

    /** 操作描述（中文） */
    String content() default "";

    /** 检索关键字 */
    String keyword() default "";
}