package com.example.scaffold.common.context;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 登录用户解析接缝：占位实现从请求头取，生产替换为 Token/会话/网关透传解析。
 *
 * @author scaffold
 * @date 2026-11-19
 */
public interface UserResolver {

    /**
     * 解析当前请求的登录用户。
     *
     * @return 已登录用户；未登录返回 null
     */
    UserInfo resolve(HttpServletRequest request);
}