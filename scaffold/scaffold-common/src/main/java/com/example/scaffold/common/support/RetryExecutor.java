package com.example.scaffold.common.support;

import com.example.scaffold.common.exception.BusinessException;
import com.example.scaffold.common.exception.CommonException;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 统一重试机制：指数退避 + 抖动 + 次数上限 + 可重试谓词。
 * BusinessException（业务失败）永不重试；只对幂等操作 + 网络/5xx 等可重试异常重试，避免重试风暴。
 *
 * @author scaffold
 * @date 2026-11-19
 */
@Slf4j
public final class RetryExecutor {

    private RetryExecutor() {
    }

    /**
     * @param desc         中文描述，便于日志追踪
     * @param maxAttempts  最大尝试次数（含首次）
     * @param baseBackoffMs 退避基数毫秒
     * @param maxBackoffMs  退避上限毫秒
     * @param retryOn      可重试异常谓词（网络/超时/5xx）
     * @param action       业务动作
     */
    public static <T> T call(String desc, int maxAttempts, long baseBackoffMs, long maxBackoffMs,
                             Predicate<Throwable> retryOn, Supplier<T> action) {
        int attempts = Math.max(1, maxAttempts);
        RuntimeException last = null;
        for (int attempt = 1; attempt <= attempts; attempt++) {
            try {
                return action.get();
            } catch (RuntimeException e) {
                last = e;
                boolean canRetry = !(e instanceof BusinessException) && retryOn.test(e) && attempt < attempts;
                if (!canRetry) {
                    throw e;
                }
                long backoff = backoff(baseBackoffMs, maxBackoffMs, attempt);
                log.warn("[{}]第{}次调用失败，{}ms 后重试，原因：{}", desc, attempt, backoff, e.getMessage());
                sleep(backoff);
            }
        }
        throw last;
    }

    private static long backoff(long base, long max, int attempt) {
        long exponential = base * (1L << Math.min(attempt - 1, 16));
        long capped = Math.min(exponential, max);
        long jitter = ThreadLocalRandom.current().nextLong(0, Math.max(1, base));
        return capped + jitter;
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            throw new BusinessException(CommonException.Common.SYSTEM_ERROR, "重试等待被中断");
        }
    }
}