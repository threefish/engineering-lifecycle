package com.example.scaffold.common.exception;

import org.springframework.util.StringUtils;

/**
 * 断言助手：优先用它抛业务异常，不裸 throw。
 *
 * @author scaffold
 * @date 2026-11-19
 */
public final class Assert {

    private Assert() {
    }

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new BusinessException(CommonException.Common.INVALID_PARAMS, message);
        }
    }

    public static void isTrue(boolean expression, ExceptionType type, Object... args) {
        if (!expression) {
            throw new BusinessException(type, args);
        }
    }

    public static void notNull(Object obj, String message) {
        if (obj == null) {
            throw new BusinessException(CommonException.Common.INVALID_PARAMS, message);
        }
    }

    public static void notNull(Object obj, ExceptionType type, Object... args) {
        if (obj == null) {
            throw new BusinessException(type, args);
        }
    }

    public static void notBlank(String text, String message) {
        if (!StringUtils.hasText(text)) {
            throw new BusinessException(CommonException.Common.INVALID_PARAMS, message);
        }
    }
}