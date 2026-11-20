package com.example.scaffold.common.web;

import com.example.scaffold.common.annotation.ManageApi;
import com.example.scaffold.common.annotation.OpenApi;
import com.example.scaffold.common.annotation.PublicApi;
import com.example.scaffold.common.annotation.RestApi;
import com.example.scaffold.common.constant.RoutePrefix;
import com.example.scaffold.common.exception.BusinessException;
import com.example.scaffold.common.exception.CommonException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * 门面分类拦截器：校验 Controller 标记注解与实际路由前缀一致，并把门面类型写入 MDC。
 *
 * <p>同一 Controller 标注多个门面注解或其路由不以对应前缀开头，视为编码错误，直接 500 暴露。</p>
 *
 * @author scaffold
 * @date 2026-11-19
 */
@Slf4j
@Component
public class ApiCategoryInterceptor implements HandlerInterceptor {

    /** MDC 键：门面类型，logback pattern 中以 [%X{apiCategory}] 输出 */
    public static final String MDC_API_CATEGORY = "apiCategory";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }
        Class<?> beanType = handlerMethod.getBeanType();
        List<String> categories = resolveCategories(beanType);
        if (categories.isEmpty()) {
            return true;
        }
        if (categories.size() > 1) {
            throw new BusinessException(CommonException.Common.SYSTEM_ERROR,
                    "门面标记冲突：" + beanType.getName() + " 同时标注了 " + categories);
        }
        String category = categories.get(0);
        String path = request.getRequestURI();
        String expectedPrefix = prefixOf(category);
        if (!path.startsWith(expectedPrefix)) {
            throw new BusinessException(CommonException.Common.SYSTEM_ERROR,
                    "门面标记与前缀不匹配：" + beanType.getName() + " 标注@" + category
                            + "，路由应为 " + expectedPrefix + " 开头，实际 " + path);
        }
        MDC.put(MDC_API_CATEGORY, category);
        log.debug("门面类型[{}] uri={}", category, path);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        MDC.remove(MDC_API_CATEGORY);
    }

    private List<String> resolveCategories(Class<?> beanType) {
        List<String> categories = new ArrayList<>(1);
        if (AnnotatedElementUtils.hasAnnotation(beanType, ManageApi.class)) {
            categories.add("MANAGE");
        }
        if (AnnotatedElementUtils.hasAnnotation(beanType, RestApi.class)) {
            categories.add("REST");
        }
        if (AnnotatedElementUtils.hasAnnotation(beanType, OpenApi.class)) {
            categories.add("OPEN");
        }
        if (AnnotatedElementUtils.hasAnnotation(beanType, PublicApi.class)) {
            categories.add("PUBLIC");
        }
        return categories;
    }

    private String prefixOf(String category) {
        return switch (category) {
            case "MANAGE" -> RoutePrefix.MANAGE_API;
            case "REST" -> RoutePrefix.REST_API;
            case "OPEN" -> RoutePrefix.OPEN_API;
            case "PUBLIC" -> RoutePrefix.PUBLIC_API;
            default -> throw new IllegalArgumentException("未知门面类型：" + category);
        };
    }
}