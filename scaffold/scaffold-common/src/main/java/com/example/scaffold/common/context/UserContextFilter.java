package com.example.scaffold.common.context;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 用户上下文过滤器：请求进入时解析登录用户写入 {@link UserContext}，请求结束必然清理，防止线程池串号。
 *
 * @author scaffold
 * @date 2026-11-19
 */
public class UserContextFilter extends OncePerRequestFilter {

    private final UserResolver userResolver;

    public UserContextFilter(UserResolver userResolver) {
        this.userResolver = userResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        try {
            UserInfo user = userResolver.resolve(request);
            if (user != null) {
                UserContext.set(user);
            }
            chain.doFilter(request, response);
        } finally {
            UserContext.clear();
        }
    }
}