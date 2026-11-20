-- 幂等种子：依赖 t_config.config_key 唯一索引（见 schema.sql），重复执行自动忽略。
-- create_date_time/update_date_time 为 INTEGER 毫秒时间戳（LocalDateTimeMillisTypeHandler 读写），
-- 原始 SQL 不走 MyBatis，需显式赋值：1767196800000 = 2026-01-01 00:00:00 GMT+8。
-- 种子无登录上下文，审计人及其显示名显式写 system。
INSERT OR IGNORE INTO t_config (id, config_key, config_value, remark,
                                create_user, create_user_desc, create_date_time,
                                update_user, update_user_desc, update_date_time)
VALUES (1, 'note.title.max-length', '100', '备忘录标题最大长度',
        'system', 'system', 1767196800000,
        'system', 'system', 1767196800000);