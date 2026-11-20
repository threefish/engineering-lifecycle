package com.example.scaffold.acl.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 外部演示客户端强类型配置：地址、连接/读超时、重试次数与退避。密钥只引用安全位置，不硬编码。
 *
 * @author scaffold
 * @date 2026-11-19
 */
@Data
@Configuration
@ConfigurationProperties(prefix = DemoClientProperties.PREFIX)
public class DemoClientProperties {

    public static final String PREFIX = "app.acl.demo";

    /** 外部服务基地址 */
    private String baseUrl = "http://localhost:9999";

    /** 连接超时毫秒 */
    private int connectTimeoutMillis = 2000;

    /** 读超时毫秒 */
    private int readTimeoutMillis = 3000;

    /** 最大尝试次数（含首次） */
    private int maxAttempts = 3;

    /** 退避基数毫秒 */
    private long backoffBaseMillis = 200;

    /** 退避上限毫秒 */
    private long backoffMaxMillis = 2000;
}