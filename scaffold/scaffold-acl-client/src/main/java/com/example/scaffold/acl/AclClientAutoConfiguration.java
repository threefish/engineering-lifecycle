package com.example.scaffold.acl;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * 防腐层自动装配：扫描外部 Client 实现与强类型客户端配置。
 *
 * @author scaffold
 * @date 2026-11-19
 */
@AutoConfiguration
@ComponentScan(basePackageClasses = AclClientAutoConfiguration.class)
public class AclClientAutoConfiguration {
}