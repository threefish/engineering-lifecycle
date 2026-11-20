package com.example.scaffold.common.lock;

import com.example.scaffold.common.exception.BusinessException;
import com.example.scaffold.common.exception.CommonException;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

/**
 * 锁工具【JVM 占位】：单机按 key 用 ReentrantLock 互斥，明确等待时间防长时间阻塞。
 * 分布式部署接缝：把内部实现替换为 Redisson（runWithTryLock 签名不变，业务无感）；
 * JVM 版不强制 leaseTime 超时释放，临界区务必短小并在 finally 释放。
 *
 * @author scaffold
 * @date 2026-11-19
 */
public final class LockHelper {

    private static final ConcurrentHashMap<String, ReentrantLock> LOCKS = new ConcurrentHashMap<>();

    private static final long DEFAULT_WAIT_SECONDS = 3L;

    private LockHelper() {
    }

    public static <T> T runWithTryLock(Supplier<T> action, String lockKey) {
        ReentrantLock lock = LOCKS.computeIfAbsent(lockKey, k -> new ReentrantLock());
        boolean acquired = false;
        try {
            acquired = lock.tryLock(DEFAULT_WAIT_SECONDS, TimeUnit.SECONDS);
            if (!acquired) {
                throw new BusinessException(CommonException.Common.LOCK_TIMEOUT, "操作[" + lockKey + "]繁忙，请稍后重试");
            }
            return action.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new BusinessException(CommonException.Common.SYSTEM_ERROR, "锁等待被中断：" + lockKey);
        } finally {
            if (acquired && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}