package com.example.scaffold.acl.support;

import com.example.scaffold.common.exception.BusinessException;
import com.example.scaffold.common.exception.ExceptionType;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 外部异常归一化：把外部原始异常转成内部 BusinessException，业务层不感知 HTTP/SDK 差异。
 * 可重试判定：网络/传输异常（IOException 家族，含连接/读超时）可重试；外部业务失败不可重试。
 * 接入真实 HTTP 客户端时，在此把 5xx（HttpServerErrorException/ResourceAccessException）纳入可重试白名单。
 *
 * @author scaffold
 * @date 2026-11-19
 */
@Slf4j
public final class ClientExceptions {

    private ClientExceptions() {
    }

    public static BusinessException normalize(Throwable e, ExceptionType code, String desc) {
        if (e instanceof BusinessException be) {
            return be;
        }
        boolean retryable = isRetryable(e);
        log.warn("外部调用失败 desc={} retryable={}", desc, retryable, e);
        return new BusinessException(code, desc, e);
    }

    public static boolean isRetryable(Throwable e) {
        if (e instanceof IOException) {
            return true;
        }
        Throwable cause = e.getCause();
        if (cause != null && cause != e) {
            return isRetryable(cause);
        }
        return false;
    }
}