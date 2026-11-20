package com.example.scaffold.common.config;

import com.example.scaffold.common.web.ApiCategoryInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * MVC 配置：注册门面分类拦截器，所有路径统一校验标记注解与前缀一致性。
 *
 * @author scaffold
 * @date 2026-11-19
 */
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final ApiCategoryInterceptor apiCategoryInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiCategoryInterceptor).addPathPatterns("/**");
    }
}