package com.example.scaffold.common.exception;

import org.springframework.http.HttpStatus;

/**
 * 错误码抽象：所有业务域错误码枚举实现它。
 * 每个错误码自带 HTTP 状态（默认 200，即业务异常走统一信封）；不存在→404、无权限→403、参数/状态非法→400 等按需覆盖。
 *
 * @author scaffold
 * @date 2026-11-19
 */
public interface ExceptionType {

    int getCode();

    String getDescription();

    /** 该错误码对应的 HTTP 状态，默认 200（业务失败仍以信封返回） */
    default HttpStatus getHttpStatus() {
        return HttpStatus.OK;
    }
}