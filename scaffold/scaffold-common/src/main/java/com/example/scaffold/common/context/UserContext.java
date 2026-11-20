package com.example.scaffold.common.context;

import com.example.scaffold.common.exception.BusinessException;
import com.example.scaffold.common.exception.CommonException;
import org.slf4j.MDC;

/**
 * 当前登录用户上下文：ThreadLocal 保存用户快照，同步写入 MDC 供日志输出。
 *
 * <p>仅在请求线程内有效；异步线程通过 {@code ThreadPoolHolder} 复制 MDC 后"日志可见"用户，
 * 但 ThreadLocal 不跨线程，异步任务内做业务判断需显式传递 {@link UserInfo}。</p>
 *
 * @author scaffold
 * @date 2026-11-19
 */
public final class UserContext {

    /** MDC 键：当前用户，格式 userId:userName，日志 pattern 以 [%X{user}] 输出 */
    public static final String MDC_USER = "user";

    private static final ThreadLocal<UserInfo> HOLDER = new ThreadLocal<>();

    private UserContext() {
    }

    public static void set(UserInfo user) {
        if (user == null) {
            clear();
            return;
        }
        HOLDER.set(user);
        MDC.put(MDC_USER, user.userId() + ":" + user.userName());
    }

    public static UserInfo get() {
        return HOLDER.get();
    }

    public static boolean isLogin() {
        return HOLDER.get() != null;
    }

    public static String getUserId() {
        UserInfo user = HOLDER.get();
        return user == null ? null : user.userId();
    }

    public static String getUserName() {
        UserInfo user = HOLDER.get();
        return user == null ? null : user.userName();
    }

    /**
     * 断言已登录：未登录抛 UNAUTHORIZED(100006)/401。
     */
    public static UserInfo requireLogin() {
        UserInfo user = HOLDER.get();
        if (user == null) {
            throw new BusinessException(CommonException.Common.UNAUTHORIZED);
        }
        return user;
    }

    public static void clear() {
        HOLDER.remove();
        MDC.remove(MDC_USER);
    }
}