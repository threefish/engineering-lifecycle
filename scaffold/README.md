# scaffold

按 [engineering-lifecycle](../README.md) 规范搭建的分层 Spring Boot 脚手架，可作为「规范怎么落地」的可运行样例，也可直接当新项目骨架。

## 技术栈

Spring Boot 3.5 · Java 17 · MyBatis-Plus · SQLite3 · springdoc-openapi

## 运行

```bash
mvn spring-boot:run
```

- 服务：http://localhost:8080
- 接口文档：http://localhost:8080/swagger-ui.html
- 数据：`./data/scaffold.db`（首次启动自动建表并写入种子数据）

## 模块

| 模块 | 职责 |
|---|---|
| `scaffold-common` | 统一返回、异常、分页、权限、操作日志、锁、TraceId、用户上下文等基础能力 |
| `scaffold-model` | 请求 / 响应模型、DTO、错误码 |
| `scaffold-acl-client` | 防腐层：外部系统客户端与自动装配（示例为占位实现） |
| `scaffold-infrastructure` | 持久化、Mapper、类型处理器 |
| `scaffold-domain` | 业务服务与领域逻辑 |
| `scaffold-facade` | 对外接口（管理侧 / 开放侧），不含业务逻辑、不加事务 |
| `scaffold-runner` | 启动模块与配置 |

分层依赖方向单向向下：`facade → domain → infrastructure → model`，跨层调用属架构违规。示例业务为一个 Note 的管理侧增删改查。
