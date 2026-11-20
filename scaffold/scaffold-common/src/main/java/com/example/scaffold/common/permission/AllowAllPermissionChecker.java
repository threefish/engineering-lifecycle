package com.example.scaffold.common.permission;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 权限校验默认实现【占位】：登录后一律放行并记 debug 日志（登录门禁由 PermissionAspect 统一断言），
 * 保证脚手架开箱可用。接入真实鉴权（如 Spring Security / 自研鉴权中心）时，
 * 实现 {@link PermissionChecker} 并替换本 Bean。
 *
 * @author scaffold
 * @date 2026-11-19
 */
@Slf4j
@Component
public class AllowAllPermissionChecker implements PermissionChecker {

    @Override
    public boolean hasPermission(String permission) {
        log.debug("权限校验占位放行 permission={}", permission);
        return true;
    }
}