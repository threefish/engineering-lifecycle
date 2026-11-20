package com.example.scaffold.infrastructure.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.scaffold.infrastructure.repository.entity.OperationLogEntity;

/**
 * 操作日志 Mapper：仅继承 MP BaseMapper，CRUD 能力由 MyBatis-Plus 提供；由 @MapperScan 注册。
 *
 * @author scaffold
 * @date 2026-11-19
 */
public interface OperationLogMapper extends BaseMapper<OperationLogEntity> {
}