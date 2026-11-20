package com.example.scaffold.facade;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * facade 自动装配：扫描 Controller（@RestController 即 @Component）。
 *
 * @author scaffold
 * @date 2026-11-19
 */
@AutoConfiguration
@ComponentScan(basePackageClasses = FacadeAutoConfiguration.class)
public class FacadeAutoConfiguration {
}