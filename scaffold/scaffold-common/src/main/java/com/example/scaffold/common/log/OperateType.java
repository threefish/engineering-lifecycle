package com.example.scaffold.common.log;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 操作类型。
 *
 * @author scaffold
 * @date 2026-11-19
 */
@Getter
@AllArgsConstructor
public enum OperateType {

    QUERY("查询"),
    SAVE_OR_UPDATE("保存或更新"),
    DELETE("删除"),
    ENABLE_OR_DISABLE("启用或停用"),
    OTHER("其他"),
    ;

    private final String label;
}