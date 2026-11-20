package com.example.scaffold.infrastructure.repository.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.example.scaffold.common.entity.BaseEntity;
import com.example.scaffold.model.enums.NoteStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 备忘录实体。大文本/JSON 列用 TEXT（SQLite 无 LONGTEXT）；
 * tags 走 JacksonTypeHandler 存 JSON 文本，autoResultMap 保证 select 时按 TypeHandler 回读。
 *
 * @author scaffold
 * @date 2026-11-19
 */
@TableName(value = "t_note", autoResultMap = true)
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class NoteEntity extends BaseEntity {

    /** 标题 */
    private String title;

    /** 正文（大文本） */
    private String content;

    /** 标签，按 JSON 存储 */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> tags;

    /** 状态（默认 EnumTypeHandler 存枚举名） */
    private NoteStatus status;

    /** 逻辑删除标记（手动维护，查询显式带 deleted=false） */
    private Boolean deleted;
}