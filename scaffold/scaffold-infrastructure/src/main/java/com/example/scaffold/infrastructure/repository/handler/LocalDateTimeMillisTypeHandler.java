package com.example.scaffold.infrastructure.repository.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * LocalDateTime ↔ epoch 毫秒：SQLite 列亲和为数值，统一存整数毫秒（与 data.sql 种子一致），
 * 时区固定 GMT+8（与 Jackson 输出一致）。经 mybatis-plus.type-handlers-package 全局注册。
 *
 * @author scaffold
 * @date 2026-11-19
 */
public class LocalDateTimeMillisTypeHandler extends BaseTypeHandler<LocalDateTime> {

    private static final ZoneOffset ZONE = ZoneOffset.ofHours(8);

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, LocalDateTime parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setLong(i, parameter.toInstant(ZONE).toEpochMilli());
    }

    @Override
    public LocalDateTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return toLocalDateTime(rs.getLong(columnName), rs.wasNull());
    }

    @Override
    public LocalDateTime getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return toLocalDateTime(rs.getLong(columnIndex), rs.wasNull());
    }

    @Override
    public LocalDateTime getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return toLocalDateTime(cs.getLong(columnIndex), cs.wasNull());
    }

    private LocalDateTime toLocalDateTime(long millis, boolean wasNull) {
        return wasNull ? null : LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZONE);
    }
}