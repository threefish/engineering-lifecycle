package com.example.scaffold.common.context;

/**
 * 当前登录用户快照（占位模型）：真实接入时可扩展部门、租户、角色等字段。
 *
 * @param userId   用户唯一标识，非空
 * @param userName 展示名，为空时回退 userId
 * @author scaffold
 * @date 2026-11-19
 */
public record UserInfo(String userId, String userName) {

    public UserInfo {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("userId 不能为空");
        }
        if (userName == null || userName.isBlank()) {
            userName = userId;
        }
    }
}