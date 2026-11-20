package com.example.scaffold.domain.config.impl;

import com.example.scaffold.common.config.ConfigService;
import com.example.scaffold.common.exception.BusinessException;
import com.example.scaffold.common.exception.CommonException;
import com.example.scaffold.infrastructure.repository.ConfigRepository;
import com.example.scaffold.infrastructure.repository.entity.ConfigEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 业务配置服务实现：本地缓存 + 未命中查 DB + refresh 清缓存。
 * 单机脚手架用 JVM 本地缓存占位；分布式部署接缝：把缓存替换为 Redis、刷新靠事件广播（接口不变）。
 *
 * @author scaffold
 * @date 2026-11-19
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ConfigServiceImpl implements ConfigService {

    private final ConfigRepository configRepository;

    private final ConcurrentHashMap<String, String> cache = new ConcurrentHashMap<>();

    @Override
    @Transactional(readOnly = true)
    public String getValue(String key) {
        String cached = cache.get(key);
        if (cached != null) {
            log.debug("配置[{}]命中本地缓存", key);
            return cached;
        }
        String value = configRepository.findByConfigKey(key)
                .map(ConfigEntity::getConfigValue)
                .orElseThrow(() -> new BusinessException(CommonException.Config.NOT_EXIST,
                        String.format(CommonException.Config.NOT_EXIST.getDescription(), key)));
        cache.put(key, value);
        log.debug("配置[{}]未命中缓存，查库加载并写入本地缓存", key);
        return value;
    }

    @Override
    @Transactional(readOnly = true)
    public String getValue(String key, String defaultValue) {
        String cached = cache.get(key);
        if (cached != null) {
            log.debug("配置[{}]命中本地缓存", key);
            return cached;
        }
        return configRepository.findByConfigKey(key)
                .map(ConfigEntity::getConfigValue)
                .map(v -> {
                    cache.put(key, v);
                    log.debug("配置[{}]未命中缓存，查库加载并写入本地缓存", key);
                    return v;
                })
                .orElse(defaultValue);
    }

    @Override
    @Transactional(readOnly = true)
    public <T> T getValue(String key, Class<T> type) {
        return convert(getValue(key), type);
    }

    @Override
    @Transactional(readOnly = true)
    public int getInt(String key, int defaultValue) {
        String value = getValue(key, String.valueOf(defaultValue));
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            log.warn("配置[{}]值[{}]非法整数，回退默认{}", key, value, defaultValue);
            return defaultValue;
        }
    }

    @Override
    public void refresh() {
        cache.clear();
        log.info("业务配置本地缓存已刷新");
    }

    @SuppressWarnings("unchecked")
    private <T> T convert(String value, Class<T> type) {
        if (type == String.class) {
            return (T) value;
        }
        if (type == Integer.class || type == int.class) {
            return (T) Integer.valueOf(value.trim());
        }
        if (type == Long.class || type == long.class) {
            return (T) Long.valueOf(value.trim());
        }
        if (type == Boolean.class || type == boolean.class) {
            return (T) Boolean.valueOf(value.trim());
        }
        throw new BusinessException(CommonException.Common.SYSTEM_ERROR, "不支持的配置类型：" + type.getName());
    }
}