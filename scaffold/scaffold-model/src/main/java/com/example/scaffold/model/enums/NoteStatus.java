package com.example.scaffold.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 备忘录状态。
 *
 * @author scaffold
 * @date 2026-11-19
 */
@Getter
@AllArgsConstructor
public enum NoteStatus {

    ENABLED("启用"),
    DISABLED("停用"),
    ;

    private final String label;
}