package com.example.scaffold.common.transaction;

import com.example.scaffold.common.concurrent.ThreadPoolHolder;
import com.example.scaffold.common.exception.BusinessException;
import com.example.scaffold.common.exception.CommonException;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * 事务后置执行器：调外部系统/发事件等副作用必须在事务提交后执行，避免「事务回滚但副作用已生效」。
 *
 * @author scaffold
 * @date 2026-11-19
 */
public final class AfterCommitExecutor {

    private AfterCommitExecutor() {
    }

    /** 同步执行：必须在活跃事务中调用，否则抛异常 */
    public static void runAfterCommit(String desc, Runnable action) {
        if (!TransactionSynchronizationManager.isSynchronizationActive()) {
            throw new BusinessException(CommonException.Common.SYSTEM_ERROR, "无活跃事务，无法注册事务后置操作：" + desc);
        }
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                action.run();
            }
        });
    }

    /** 异步执行：提交后投递统一线程池，失败记日志，不回滚已提交的主事务 */
    public static void runAfterCommitAsync(String desc, Runnable action) {
        runAfterCommit(desc, () -> ThreadPoolHolder.execute(desc, action));
    }
}