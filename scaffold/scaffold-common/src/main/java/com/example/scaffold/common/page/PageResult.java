package com.example.scaffold.common.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * 统一分页返回体。
 *
 * @author scaffold
 * @date 2026-11-19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {

    /** 当前页数据 */
    private List<T> records;

    /** 总条数 */
    private long total;

    /** 当前页码 */
    private int pageNo;

    /** 每页条数 */
    private int pageSize;

    /** 转换当前页记录，分页元信息不变 */
    public <R> PageResult<R> map(Function<? super T, ? extends R> mapper) {
        List<R> mapped = new ArrayList<>(records.size());
        for (T record : records) {
            mapped.add(mapper.apply(record));
        }
        return new PageResult<>(mapped, total, pageNo, pageSize);
    }
}