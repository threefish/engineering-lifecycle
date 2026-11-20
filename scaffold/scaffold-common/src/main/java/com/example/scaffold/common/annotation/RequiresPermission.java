package com.example.scaffold.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限码声明【占位】：由 PermissionAspect 织入校验，默认 AllowAllPermissionChecker 全放行；
 * 接入项目权限体系时替换 PermissionChecker 实现即可。
 *
 * @author scaffold
 * @date 2026-11-19
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiresPermission {

    /** 权限码 */
    String value();
}