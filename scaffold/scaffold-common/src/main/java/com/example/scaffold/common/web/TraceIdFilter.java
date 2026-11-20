package com.example.scaffold.common.web;

import com.example.scaffold.common.util.TraceIdHolder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * 追踪号过滤器：优先透传上游 X-Trace-Id（白名单字符校验，防日志注入），否则生成；
 * 写入 MDC 与响应头，请求结束清理。
 *
 * @author scaffold
 * @date 2026-11-19
 */
@Slf4j
public class TraceIdFilter extends OncePerRequestFilter {

    public static final String HEADER = "X-Trace-Id";

    /** 上游 traceId 白名单：仅字母/数字/连字符，1~64 位 */
    private static final Pattern TRACE_ID_PATTERN = Pattern.compile("^[A-Za-z0-9-]{1,64}$");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String traceId = request.getHeader(HEADER);
        if (!StringUtils.hasText(traceId)) {
            traceId = UUID.randomUUID().toString().replace("-", "");
        } else if (!TRACE_ID_PATTERN.matcher(traceId).matches()) {
            log.debug("非法 X-Trace-Id，已重新生成");
            traceId = UUID.randomUUID().toString().replace("-", "");
        }
        TraceIdHolder.set(traceId);
        response.setHeader(HEADER, traceId);
        try {
            chain.doFilter(request, response);
        } finally {
            TraceIdHolder.clear();
        }
    }
}