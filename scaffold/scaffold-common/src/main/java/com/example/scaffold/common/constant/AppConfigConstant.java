package com.example.scaffold.common.constant;

/**
 * DB 业务配置 key 常量：集中管理，新增业务参数优先走这里而非硬编码或写死 yml。
 *
 * @author scaffold
 * @date 2026-11-19
 */
public final class AppConfigConstant {

    private AppConfigConstant() {
    }

    public interface Note {
        /** 备忘录标题最大长度 */
        String TITLE_MAX_LENGTH = "note.title.max-length";
    }
}