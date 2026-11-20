package com.example.scaffold.runner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * 启动类：唯一可执行入口。默认只扫自身包，其余模块 Bean 由各自 AutoConfiguration.imports 装配。
 * 启动前先建 SQLite 数据目录（jdbc:sqlite:./data/scaffold.db 的父目录不会自动创建）。
 *
 * @author scaffold
 * @date 2026-11-19
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) throws IOException {
        Files.createDirectories(Path.of("data"));
        SpringApplication.run(Application.class, args);
    }
}