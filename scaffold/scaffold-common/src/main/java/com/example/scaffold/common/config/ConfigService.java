package com.example.scaffold.common.config;

/**
 * 业务配置服务接口：开关/阈值/可变参数走 DB 配置，运行时可调、无需发版。
 * 接口置于 common 供任意层调用，实现置于 domain（直查 DB + 本地缓存），避免 common 反向依赖 infrastructure。
 *
 * @author scaffold
 * @date 2026-11-19
 */
public interface ConfigService {

    /** 取字符串值，不存在抛业务异常 */
    String getValue(String key);

    /** 取字符串值，不存在返回默认值 */
    String getValue(String key, String defaultValue);

    /** 取值并转换为目标类型 */
    <T> T getValue(String key, Class<T> type);

    /** 取整数值，不存在或非法返回默认值 */
    int getInt(String key, int defaultValue);

    /** 清空本地缓存，下次取值重新查库 */
    void refresh();
}