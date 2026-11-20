package com.example.scaffold.infrastructure.repository.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.scaffold.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 业务配置实体：config_key 唯一（唯一索引由 schema.sql 维护），支撑 INSERT OR IGNORE 幂等种子与运行时取值。
 *
 * @author scaffold
 * @date 2026-11-19
 */
@TableName("t_config")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ConfigEntity extends BaseEntity {

    /** 配置键 */
    private String configKey;

    /** 配置值 */
    private String configValue;

    /** 备注 */
    private String remark;
}