package com.example.scaffold.infrastructure;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * 基础设施自动装配：多模块 MyBatis-Plus 生效枢纽。
 * runner 默认只扫自身包，故在此显式声明 Mapper 扫描包，并扫描本包的仓储实现/配置/转换器组件。
 *
 * @author scaffold
 * @date 2026-11-19
 */
@AutoConfiguration
@ComponentScan(basePackageClasses = InfrastructureAutoConfiguration.class)
@MapperScan(basePackages = "com.example.scaffold.infrastructure.repository.mapper")
public class InfrastructureAutoConfiguration {
}