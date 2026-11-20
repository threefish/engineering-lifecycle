-- 建表脚本：MyBatis-Plus 不生成 DDL，由 spring.sql.init 在启动时执行（IF NOT EXISTS 幂等）。
-- 主键由应用侧雪花（IdType.ASSIGN_ID）生成，故为普通 INTEGER PRIMARY KEY（非自增）。
-- 时间戳列为 INTEGER epoch 毫秒（由 LocalDateTimeMillisTypeHandler 读写），与 data.sql 种子一致。
CREATE TABLE IF NOT EXISTS t_note (
    id               INTEGER PRIMARY KEY,
    title            VARCHAR(100) NOT NULL,
    content          TEXT,
    tags             TEXT,
    status           VARCHAR(16),
    deleted          INTEGER NOT NULL DEFAULT 0,
    create_user      VARCHAR(64),
    create_user_desc VARCHAR(64),
    create_date_time INTEGER NOT NULL,
    update_user      VARCHAR(64),
    update_user_desc VARCHAR(64),
    update_date_time INTEGER NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_note_status ON t_note (status, deleted, update_date_time);

CREATE TABLE IF NOT EXISTS t_config (
    id               INTEGER PRIMARY KEY,
    config_key       VARCHAR(100) NOT NULL,
    config_value     TEXT,
    remark           VARCHAR(200),
    create_user      VARCHAR(64),
    create_user_desc VARCHAR(64),
    create_date_time INTEGER NOT NULL,
    update_user      VARCHAR(64),
    update_user_desc VARCHAR(64),
    update_date_time INTEGER NOT NULL,
    CONSTRAINT uc_config_key UNIQUE (config_key)
);

CREATE TABLE IF NOT EXISTS t_operation_log (
    id               INTEGER PRIMARY KEY,
    operate_type     VARCHAR(32),
    content          VARCHAR(500),
    keyword          VARCHAR(100),
    method           VARCHAR(200),
    params           TEXT,
    business_id      INTEGER,
    operate_user     VARCHAR(64),
    trace_id         VARCHAR(64),
    cost_ms          INTEGER,
    success          INTEGER,
    error_message    VARCHAR(500),
    operate_time     INTEGER,
    create_user      VARCHAR(64),
    create_user_desc VARCHAR(64),
    create_date_time INTEGER NOT NULL,
    update_user      VARCHAR(64),
    update_user_desc VARCHAR(64),
    update_date_time INTEGER NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_oplog_trace ON t_operation_log (trace_id);
CREATE INDEX IF NOT EXISTS idx_oplog_time ON t_operation_log (operate_time);