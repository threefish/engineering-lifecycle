package com.example.scaffold.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 业务异常（静默业务失败，不触发错误告警）：禁止裸抛 RuntimeException 表达业务失败。
 * 携带错误码与对应 HTTP 状态，GlobalExceptionAdvice 直接按状态返回，不再硬编码特判。
 *
 * @author scaffold
 * @date 2026-11-19
 */
@Getter
public class BusinessException extends RuntimeException {

    private final int code;

    private final HttpStatus httpStatus;

    public BusinessException(ExceptionType type) {
        super(type.getDescription());
        this.code = type.getCode();
        this.httpStatus = type.getHttpStatus();
    }

    /** 显式文案（覆盖错误码模板） */
    public BusinessException(ExceptionType type, String message) {
        super(message);
        this.code = type.getCode();
        this.httpStatus = type.getHttpStatus();
    }

    /** 以错误码模板格式化参数：new BusinessException(Note.TITLE_TOO_LONG, 100) */
    public BusinessException(ExceptionType type, Object... args) {
        super(String.format(type.getDescription(), args));
        this.code = type.getCode();
        this.httpStatus = type.getHttpStatus();
    }

    public BusinessException(ExceptionType type, String message, Throwable cause) {
        super(message, cause);
        this.code = type.getCode();
        this.httpStatus = type.getHttpStatus();
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
        this.httpStatus = HttpStatus.OK;
    }
}