package com.example.scaffold.common.permission;

/**
 * 权限校验接口：{@code @RequiresPermission} 的唯一校验入口。
 * 默认实现 {@link AllowAllPermissionChecker} 放行占位；接入真实鉴权时实现本接口并注册为 Bean 即可，
 * 无需改动任何 Controller 上的注解。
 *
 * @author scaffold
 * @date 2026-11-19
 */
public interface PermissionChecker {

    /** 当前调用者是否拥有指定权限码 */
    boolean hasPermission(String permission);
}