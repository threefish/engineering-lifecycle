package com.example.scaffold.common.config;

import com.example.scaffold.common.concurrent.ThreadPoolHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 统一异步线程池：命名 scaffold-async-、core2/max4/queue256、CallerRunsPolicy 背压。
 *
 * @author scaffold
 * @date 2026-11-19
 */
@Configuration
public class ThreadPoolConfig {

    @Bean
    public ThreadPoolExecutor scaffoldAsyncExecutor() {
        AtomicInteger seq = new AtomicInteger(1);
        ThreadFactory factory = r -> new Thread(r, "scaffold-async-" + seq.getAndIncrement());
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                2, 4, 60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(256),
                factory,
                new ThreadPoolExecutor.CallerRunsPolicy());
        ThreadPoolHolder.init(pool);
        return pool;
    }
}