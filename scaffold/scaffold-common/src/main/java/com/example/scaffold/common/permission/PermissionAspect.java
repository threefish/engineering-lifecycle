package com.example.scaffold.common.permission;

import com.example.scaffold.common.annotation.RequiresPermission;
import com.example.scaffold.common.context.UserContext;
import com.example.scaffold.common.exception.BusinessException;
import com.example.scaffold.common.exception.CommonException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 权限切面：环绕 {@code @RequiresPermission} 方法先断言登录（未登录 UNAUTHORIZED/401），
 * 再调用 {@link PermissionChecker}（不通过 FORBIDDEN/403）。
 * 顺序在操作日志切面之内层（precedence 数值更大），使「登录/权限拒绝」也能被操作日志记录为失败。
 *
 * @author scaffold
 * @date 2026-11-19
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE + 200)
public class PermissionAspect {

    private final PermissionChecker permissionChecker;

    @Around("@annotation(requiresPermission)")
    public Object around(ProceedingJoinPoint pjp, RequiresPermission requiresPermission) throws Throwable {
        String permission = requiresPermission.value();
        UserContext.requireLogin();
        if (!permissionChecker.hasPermission(permission)) {
            log.warn("权限校验未通过 permission={} method={}", permission, pjp.getSignature().toShortString());
            throw new BusinessException(CommonException.Common.FORBIDDEN, "无操作权限：" + permission);
        }
        return pjp.proceed();
    }
}