package com.example.scaffold.common.util;

import org.slf4j.MDC;

/**
 * 追踪号持有器：基于 SLF4J MDC，贯穿日志、返回体与异步任务。
 *
 * @author scaffold
 * @date 2026-11-19
 */
public final class TraceIdHolder {

    public static final String TRACE_ID = "traceId";

    private TraceIdHolder() {
    }

    public static String get() {
        return MDC.get(TRACE_ID);
    }

    public static void set(String traceId) {
        MDC.put(TRACE_ID, traceId);
    }

    public static void clear() {
        MDC.remove(TRACE_ID);
    }
}