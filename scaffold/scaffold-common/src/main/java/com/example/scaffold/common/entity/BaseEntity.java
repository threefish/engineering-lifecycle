package com.example.scaffold.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实体基类（对齐 canjiajiu 风格）：主键 + 审计六列。
 * 填充逻辑在 infrastructure 的 AuditMetaObjectHandler，操作人取自 common 的 UserContext。
 *
 * @author scaffold
 * @date 2026-11-19
 */
@Data
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键：MyBatis-Plus 内置雪花算法，实体显式赋值时以显式值为准（如种子） */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /** 创建人（登录用户名） */
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 创建人显示名：脚手架无昵称来源，暂与 createUser 同值，接入用户中心后替换 */
    @TableField(fill = FieldFill.INSERT)
    private String createUserDesc;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDateTime;

    /** 更新人（登录用户名） */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateUser;

    /** 更新人显示名：暂与 updateUser 同值 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateUserDesc;

    /** 更新时间 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateDateTime;
}