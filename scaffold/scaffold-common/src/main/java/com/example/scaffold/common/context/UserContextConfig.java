package com.example.scaffold.common.context;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * 用户上下文 Web 配置：注册过滤器，排在 TraceIdFilter（HIGHEST_PRECEDENCE）之后，
 * 保证日志中的 user 与 traceId 同时可用。
 *
 * @author scaffold
 * @date 2026-11-19
 */
@Configuration
@RequiredArgsConstructor
public class UserContextConfig {

    private final UserResolver userResolver;

    @Bean
    public FilterRegistrationBean<UserContextFilter> userContextFilterRegistration() {
        FilterRegistrationBean<UserContextFilter> reg = new FilterRegistrationBean<>();
        reg.setFilter(new UserContextFilter(userResolver));
        reg.addUrlPatterns("/*");
        reg.setName("userContextFilter");
        reg.setOrder(Ordered.HIGHEST_PRECEDENCE + 10);
        return reg;
    }
}