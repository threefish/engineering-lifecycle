package com.example.scaffold.infrastructure.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis-Plus 插件配置：
 * ① BlockAttack 拦截无 WHERE 的全表 UPDATE/DELETE（安全默认值）；
 * ② 分页 + 自定义 SQLite 方言（MP 3.5.18 的 DialectFactory 未内置 SQLite，返回 null 会导致分页 NPE）。
 *
 * @author scaffold
 * @date 2026-11-19
 */
@Configuration
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(new SQLiteDialect()));
        return interceptor;
    }
}