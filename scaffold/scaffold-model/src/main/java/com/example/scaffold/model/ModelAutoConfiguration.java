package com.example.scaffold.model;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * model 层自动装配：本层仅 VO/枚举/常量，无 Spring 组件，扫描为空占位以保持每模块一份装配的统一形状。
 *
 * @author scaffold
 * @date 2026-11-19
 */
@AutoConfiguration
@ComponentScan(basePackageClasses = ModelAutoConfiguration.class)
public class ModelAutoConfiguration {
}