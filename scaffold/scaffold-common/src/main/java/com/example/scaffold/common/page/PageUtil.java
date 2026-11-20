package com.example.scaffold.common.page;

import java.util.List;

/**
 * 分页工具：页码/条数归一（1 起始、封顶最大条数）与 PageResult 组装。
 * 与持久化框架无关；具体分页 SQL 由 infrastructure 的仓储实现负责。
 *
 * @author scaffold
 * @date 2026-11-19
 */
public final class PageUtil {

    /** 单页最大条数，防止一次拉全表 */
    public static final int MAX_PAGE_SIZE = 200;

    private PageUtil() {
    }

    public static int normalizePageNo(int pageNo) {
        return Math.max(pageNo, 1);
    }

    public static int normalizePageSize(int pageSize) {
        return Math.min(Math.max(pageSize, 1), MAX_PAGE_SIZE);
    }

    public static <T> PageResult<T> toResult(List<T> records, long total, int pageNo, int pageSize) {
        return new PageResult<>(records, total, pageNo, pageSize);
    }
}