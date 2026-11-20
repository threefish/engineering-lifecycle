package com.example.scaffold.acl.client.impl;

import com.example.scaffold.acl.client.DemoExternalClient;
import com.example.scaffold.acl.config.DemoClientProperties;
import com.example.scaffold.acl.model.demo.DemoNameResponse;
import com.example.scaffold.acl.support.ClientExceptions;
import com.example.scaffold.common.exception.CommonException;
import com.example.scaffold.common.support.RetryExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 外部演示客户端【占位实现】：用统一 RetryExecutor 包裹（指数退避 + 上限 + 可重试谓词），失败归一化为 BusinessException。
 * 真实项目把 doSync 替换为 HTTP（RestClient）或厂商 SDK 调用，并设置连接/读超时；接口与重试/归一化骨架不变。
 *
 * @author scaffold
 * @date 2026-11-19
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class StubDemoExternalClient implements DemoExternalClient {

    private final DemoClientProperties properties;

    @Override
    public DemoNameResponse syncName(String code, String name) {
        String desc = "同步名称到外部演示系统[" + code + ":" + name + "]";
        try {
            return RetryExecutor.call(desc, properties.getMaxAttempts(),
                    properties.getBackoffBaseMillis(), properties.getBackoffMaxMillis(),
                    ClientExceptions::isRetryable, () -> doSync(code, name));
        } catch (RuntimeException e) {
            throw ClientExceptions.normalize(e, CommonException.Integration.DEMO_ERROR, desc);
        }
    }

    private DemoNameResponse doSync(String code, String name) {
        log.info("外部演示客户端占位调用 code={} name={} baseUrl={}", code, name, properties.getBaseUrl());
        DemoNameResponse resp = new DemoNameResponse();
        resp.setSuccess(true);
        resp.setCode(code);
        resp.setMsg("占位成功");
        return resp;
    }
}