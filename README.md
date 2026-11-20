# engineering-lifecycle

> 把工程规范做成 AI 编码助手可加载的规则包：一层常驻的安全红线与项目边界，加一层按需加载的工程细则。

编码 Agent 的上下文是稀缺资源。把全部工程规范塞进一个大文件，大部分内容在单次任务里从不生效，还挤占了真正需要的代码上下文。

本仓库把这套规范拆成两层：

- **常驻层 `AGENTS.md`**：每轮会话都必须成立的约束 —— 安全与授权红线、项目边界、上下文按需加载、交互风格。体量极小。
- **按需层 `engineering-lifecycle` Skill**：详细的工程细则（分层落位、接口契约、事务、数据访问、验证交付、复盘）。只在制定方案、改代码、重构、修复 Bug、审查、补测试或复盘时加载，且按当前阶段只读取相关的一份 reference。

## 三件产出

| 目录 | 是什么 | 面向 |
|---|---|---|
| [`qoder/`](qoder/) | Qoder Desktop 规则包：`AGENTS.md` 常驻 + `skills/engineering-lifecycle/` | Qoder |
| [`codex/`](codex/) | Codex 规则包，同上（`AGENTS.md` 使用 `$CODEX_HOME` 路径，Skill 带 `agents/openai.yaml` UI 元数据） | Codex |
| [`scaffold/`](scaffold/) | 参照实现：按本规范搭的分层 Spring Boot 脚手架（MyBatis-Plus + SQLite3） | 人 / Agent |

## 设计取舍

| 决策 | 原因 |
|---|---|
| 规范拆两层，而非一个大文件 | 常驻内容每轮都占用上下文，必须最小化；细则按需加载 |
| Skill 内再按阶段路由 | 单份 reference 只在相关场景读取，避免一次性灌满 |
| 安全红线、项目边界留在常驻层 | Skill 是否被触发没有保证，红线不能依赖触发 |
| 正文不写版本号、不引用包外文档 | 分发包自足，避免口径漂移 |

冲突优先级始终是：**安全与不可逆风险 > 事实 > 对齐 > 速度**。

## 安装

### Qoder Desktop

```bash
cp -r qoder/skills/engineering-lifecycle ~/.agents/skills/
# AGENTS.md 作为全局常驻规则放在 Qoder 读取规则的位置；
# 若项目级 AGENTS.md 会替换而非合并全局规则，必须保留其中的安全红线、项目边界与 Skill 入口
```

### Codex

```bash
mkdir -p "${CODEX_HOME:-$HOME/.codex}/skills"
cp -r codex/skills/engineering-lifecycle "${CODEX_HOME:-$HOME/.codex}/skills/"
# AGENTS.md 的 Skill 入口路径：$CODEX_HOME/skills/engineering-lifecycle/SKILL.md
```

两个平台的 `SKILL.md` 与 9 份 reference **内容完全一致**，差异只在 `AGENTS.md` 的平台措辞与安装路径。

## Skill 覆盖什么

入口 [`SKILL.md`](qoder/skills/engineering-lifecycle/SKILL.md) 先给「定档 → 三问 → 对齐 → 设计 → 实现 → 卡住 → 验证 → 交付 → 复盘」的流程总览，再按阶段路由到 reference：

| 阶段 | reference |
|---|---|
| 需求对齐、定范围 | `alignment` |
| 影响推演、取舍、反方审议 | `design` |
| 分层落位、跨模块调用 | `architecture` |
| 对外接口、错误码、契约变更 | `interface-contract` |
| 写操作、事务、幂等、锁、异步 | `transaction` |
| 查询与存储、批量、迁移、性能 | `data-access` |
| 卡住时该不该停 | `escalation` |
| 验证自检、交付说明、Code Review | `validation-delivery` |
| 删除前考古、交接评审、复盘 | `retrospective` |

## 参照脚手架

`scaffold/` 是把上述规范落到真实项目的样子：Spring Boot 3.5 + Java 17 + MyBatis-Plus + SQLite3，按 `common / model / acl-client / infrastructure / domain / facade / runner` 分层，示例业务是一个 Note 的增删改查。

```bash
cd scaffold
mvn spring-boot:run          # http://localhost:8080 ，Swagger: /swagger-ui.html
```

数据落在 `./data/scaffold.db`，建表与种子脚本在启动时自动执行。它既是「规范怎么落地」的可运行样例，也可直接当新项目骨架。

## License

[MIT](LICENSE)
