package com.example.scaffold.domain;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * domain 自动装配：扫描 Service 实现、配置服务实现与事件监听器。
 *
 * @author scaffold
 * @date 2026-11-19
 */
@AutoConfiguration
@ComponentScan(basePackageClasses = DomainAutoConfiguration.class)
public class DomainAutoConfiguration {
}