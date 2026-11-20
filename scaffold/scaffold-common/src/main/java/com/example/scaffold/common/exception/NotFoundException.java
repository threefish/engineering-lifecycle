package com.example.scaffold.common.exception;

/**
 * 资源不存在或越权：携带具体业务域错误码（如 Note.NOT_EXIST），统一由 GlobalExceptionAdvice 按错误码自带的
 * HTTP 状态（404）与域错误码返回，既保留域语义又不暴露资源是否存在之外的内部细节。
 *
 * @author scaffold
 * @date 2026-11-19
 */
public class NotFoundException extends BusinessException {

    public NotFoundException(ExceptionType type, Object... args) {
        super(type, args);
    }
}