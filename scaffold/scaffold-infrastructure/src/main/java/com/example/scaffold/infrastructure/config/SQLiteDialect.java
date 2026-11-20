package com.example.scaffold.infrastructure.config;

import com.baomidou.mybatisplus.extension.plugins.pagination.DialectModel;
import com.baomidou.mybatisplus.extension.plugins.pagination.dialects.IDialect;

/**
 * SQLite 分页方言：追加 LIMIT ? OFFSET ?（SQLite 语法，参数顺序为 limit、offset）。
 *
 * @author scaffold
 * @date 2026-11-19
 */
public class SQLiteDialect implements IDialect {

    @Override
    public DialectModel buildPaginationSql(String originalSql, long offset, long limit) {
        return new DialectModel(originalSql + " LIMIT ? OFFSET ?", limit, offset).setConsumerChain();
    }
}