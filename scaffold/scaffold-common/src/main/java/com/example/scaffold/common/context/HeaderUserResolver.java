package com.example.scaffold.common.context;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

/**
 * 请求头用户解析【占位】：{@code X-User-Id} + 可选 {@code X-User-Name}，非法值按未登录处理。
 *
 * <p>仅用于脚手架演示与联调；生产接入 Token/会话/网关用户解析后替换本 Bean。
 * 请求头值受 HTTP 编码限制，用户名建议使用 ASCII，中文名应由真实鉴权链路提供。</p>
 *
 * @author scaffold
 * @date 2026-11-19
 */
@Slf4j
@Component
public class HeaderUserResolver implements UserResolver {

    public static final String HEADER_USER_ID = "X-User-Id";

    public static final String HEADER_USER_NAME = "X-User-Name";

    private static final Pattern USER_ID_PATTERN = Pattern.compile("^[A-Za-z0-9_-]{1,64}$");

    private static final int MAX_USER_NAME_LEN = 64;

    @Override
    public UserInfo resolve(HttpServletRequest request) {
        String userId = request.getHeader(HEADER_USER_ID);
        if (!StringUtils.hasText(userId)) {
            return null;
        }
        if (!USER_ID_PATTERN.matcher(userId).matches()) {
            log.debug("非法 X-User-Id，按未登录处理");
            return null;
        }
        return new UserInfo(userId, sanitizeName(request.getHeader(HEADER_USER_NAME), userId));
    }

    private String sanitizeName(String raw, String fallback) {
        if (!StringUtils.hasText(raw)) {
            return fallback;
        }
        String cleaned = raw.replaceAll("\\p{Cntrl}", "").trim();
        if (cleaned.isEmpty()) {
            return fallback;
        }
        return cleaned.length() > MAX_USER_NAME_LEN ? cleaned.substring(0, MAX_USER_NAME_LEN) : cleaned;
    }
}