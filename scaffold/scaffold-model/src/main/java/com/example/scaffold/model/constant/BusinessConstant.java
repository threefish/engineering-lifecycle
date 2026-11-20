package com.example.scaffold.model.constant;

/**
 * 业务常量：锁 key 模板集中管理，统一带项目前缀 scaffold:。
 *
 * @author scaffold
 * @date 2026-11-19
 */
public final class BusinessConstant {

    private BusinessConstant() {
    }

    public interface LockKey {
        /** 备忘录启停锁，按 id 细粒度加锁 */
        String NOTE_ENABLE_OR_DISABLE = "scaffold:note:enableOrDisable:%s";
    }
}