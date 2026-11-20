package com.example.scaffold.common;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * common 内核自动装配：扫描本包下的 Result/异常/分页/事务/锁/线程池/日志/重试等组件。
 *
 * @author scaffold
 * @date 2026-11-19
 */
@AutoConfiguration
@ComponentScan(basePackageClasses = CommonAutoConfiguration.class)
public class CommonAutoConfiguration {
}