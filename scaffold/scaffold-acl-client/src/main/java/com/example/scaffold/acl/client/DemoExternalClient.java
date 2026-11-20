package com.example.scaffold.acl.client;

import com.example.scaffold.acl.model.demo.DemoNameResponse;

/**
 * 外部演示系统客户端接口：业务层只依赖它，不感知底层是 HTTP 还是厂商 SDK。
 *
 * @author scaffold
 * @date 2026-11-19
 */
public interface DemoExternalClient {

    /**
     * 同步名称到外部系统（演示占位）。
     *
     * @param code 业务编码
     * @param name 名称
     * @return 外部响应
     */
    DemoNameResponse syncName(String code, String name);
}