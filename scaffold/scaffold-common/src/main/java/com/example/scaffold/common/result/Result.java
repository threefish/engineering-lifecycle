package com.example.scaffold.common.result;

import com.example.scaffold.common.util.SensitiveUtil;
import com.example.scaffold.common.util.TraceIdHolder;
import lombok.Data;

/**
 * 统一返回体：成功固定 code=0，业务错误用异常码；所有分支回填 ts 与 traceId，对外文案脱敏。
 *
 * @author scaffold
 * @date 2026-11-19
 */
@Data
public class Result<T> {

    /** 状态码：0 成功，非 0 为业务/系统错误码 */
    private int code;

    /** 提示文案 */
    private String msg;

    /** 业务数据 */
    private T data;

    /** 服务端时间戳（毫秒） */
    private long ts;

    /** 追踪号，便于排障 */
    private String traceId;

    public static <T> Result<T> success(T data) {
        Result<T> r = new Result<>();
        r.code = 0;
        r.msg = "ok";
        r.data = data;
        r.ts = System.currentTimeMillis();
        r.traceId = TraceIdHolder.get();
        return r;
    }

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> failed(int code, String msg) {
        Result<T> r = new Result<>();
        r.code = code;
        r.msg = SensitiveUtil.clean(msg);
        r.ts = System.currentTimeMillis();
        r.traceId = TraceIdHolder.get();
        return r;
    }
}