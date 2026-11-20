package com.example.scaffold.common.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 统一异步线程池持有器：禁止手工 new Thread；异步任务传递 MDC（traceId）并捕获异常，不静默吞掉。
 *
 * @author scaffold
 * @date 2026-11-19
 */
@Slf4j
public final class ThreadPoolHolder {

    private static volatile ThreadPoolExecutor executor;

    private ThreadPoolHolder() {
    }

    public static void init(ThreadPoolExecutor pool) {
        executor = pool;
    }

    public static void execute(String desc, Runnable task) {
        Map<String, String> context = MDC.getCopyOfContextMap();
        ThreadPoolExecutor pool = executor;
        if (pool == null) {
            log.warn("线程池未初始化，降级同步执行 desc={}", desc);
            runSafely(desc, task, context);
            return;
        }
        pool.execute(() -> runSafely(desc, task, context));
    }

    private static void runSafely(String desc, Runnable task, Map<String, String> context) {
        if (context != null) {
            MDC.setContextMap(context);
        }
        try {
            task.run();
        } catch (Exception e) {
            log.error("异步任务执行失败 desc={}", desc, e);
        } finally {
            MDC.clear();
        }
    }
}