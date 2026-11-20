package com.example.scaffold.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 集中错误码：按业务域分组的嵌套枚举，均实现 ExceptionType。
 * 号段约定：通用 10xxxx、备忘录 20xxxx、集成 30xxxx、配置 40xxxx；新增错误码在对应域内递增。
 * 每个错误码显式声明 HTTP 状态，GlobalExceptionAdvice 据此映射，避免在 Advice 里硬编码某个错误码。
 * 说明：本脚手架将异常内核整体置于 common（而非 model），以避免 common 与 model 循环依赖（详见 README 差异说明）。
 *
 * @author scaffold
 * @date 2026-11-19
 */
public final class CommonException {

    private CommonException() {
    }

    @Getter
    @AllArgsConstructor
    public enum Common implements ExceptionType {
        /** 系统兜底错误 */
        SYSTEM_ERROR(100000, "系统繁忙，请稍后重试", HttpStatus.INTERNAL_SERVER_ERROR),
        /** 参数校验失败 */
        INVALID_PARAMS(100001, "参数校验失败", HttpStatus.BAD_REQUEST),
        /** 资源不存在或越权 */
        NOT_FOUND(100002, "资源不存在或无权访问", HttpStatus.NOT_FOUND),
        /** 唯一性/外键冲突 */
        CONFLICT(100003, "数据已存在或违反唯一性约束", HttpStatus.CONFLICT),
        /** 抢锁超时 */
        LOCK_TIMEOUT(100004, "操作繁忙，请稍后重试", HttpStatus.TOO_MANY_REQUESTS),
        /** 无操作权限 */
        FORBIDDEN(100005, "无操作权限", HttpStatus.FORBIDDEN),
        /** 未登录或登录已过期 */
        UNAUTHORIZED(100006, "未登录或登录已过期", HttpStatus.UNAUTHORIZED),
        ;
        private final int code;
        private final String description;
        private final HttpStatus httpStatus;
    }

    @Getter
    @AllArgsConstructor
    public enum Note implements ExceptionType {
        /** 备忘录不存在 */
        NOT_EXIST(200001, "备忘录[%s]不存在", HttpStatus.NOT_FOUND),
        /** 标题超长 */
        TITLE_TOO_LONG(200002, "标题长度不能超过%s个字符", HttpStatus.BAD_REQUEST),
        /** 状态非法 */
        STATUS_INVALID(200003, "备忘录状态非法：%s", HttpStatus.BAD_REQUEST),
        ;
        private final int code;
        private final String description;
        private final HttpStatus httpStatus;
    }

    @Getter
    @AllArgsConstructor
    public enum Integration implements ExceptionType {
        /** 外部演示服务调用失败 */
        DEMO_ERROR(300001, "外部演示服务调用失败：%s", HttpStatus.BAD_GATEWAY),
        ;
        private final int code;
        private final String description;
        private final HttpStatus httpStatus;
    }

    @Getter
    @AllArgsConstructor
    public enum Config implements ExceptionType {
        /** 配置项不存在 */
        NOT_EXIST(400001, "配置[%s]不存在", HttpStatus.INTERNAL_SERVER_ERROR),
        ;
        private final int code;
        private final String description;
        private final HttpStatus httpStatus;
    }
}