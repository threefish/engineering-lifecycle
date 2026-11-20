package com.example.scaffold.infrastructure.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.example.scaffold.common.context.UserContext;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 审计字段填充（对齐 canjiajiu 写法）：时间必填，操作人登录时写入；
 * 统一用 setFieldValByName 强刷——strict 策略会跳过已加载实体的非空字段，update 时无法刷新。
 * 异步线程无 UserContext，操作人留空不阻断。
 *
 * @author scaffold
 * @date 2026-11-19
 */
@Component
public class AuditMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        setFieldValByName("createDateTime", LocalDateTime.now(), metaObject);
        String userName = UserContext.getUserName();
        if (userName != null) {
            setFieldValByName("createUser", userName, metaObject);
            setFieldValByName("createUserDesc", userName, metaObject);
        }
        updateFill(metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName("updateDateTime", LocalDateTime.now(), metaObject);
        String userName = UserContext.getUserName();
        if (userName != null) {
            setFieldValByName("updateUser", userName, metaObject);
            setFieldValByName("updateUserDesc", userName, metaObject);
        }
    }
}