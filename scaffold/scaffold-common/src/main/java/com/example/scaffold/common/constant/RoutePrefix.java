package com.example.scaffold.common.constant;

/**
 * 路由前缀常量：门面类型由前缀 + 标记注解区分。
 *
 * @author scaffold
 * @date 2026-11-19
 */
public final class RoutePrefix {

    private RoutePrefix() {
    }

    /** 后台管理 */
    public static final String MANAGE_API = "/manage-api";

    /** 客户端 */
    public static final String REST_API = "/rest-api";

    /** 开放接口 */
    public static final String OPEN_API = "/open-api";

    /** 公开接口 */
    public static final String PUBLIC_API = "/public-api";
}