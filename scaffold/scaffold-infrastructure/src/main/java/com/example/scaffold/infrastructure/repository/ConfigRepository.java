package com.example.scaffold.infrastructure.repository;

import com.example.scaffold.infrastructure.repository.entity.ConfigEntity;
import com.example.scaffold.infrastructure.repository.mapper.ConfigMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 业务配置仓储（对齐 canjiajiu 写法）：单类继承 BaseRepository，按唯一键 config_key 查询。
 *
 * @author scaffold
 * @date 2026-11-19
 */
@Repository
public class ConfigRepository extends BaseRepository<ConfigMapper, ConfigEntity> {

    /** 按配置键取值 */
    public Optional<ConfigEntity> findByConfigKey(String configKey) {
        return lambdaQuery().eq(ConfigEntity::getConfigKey, configKey).oneOpt();
    }
}