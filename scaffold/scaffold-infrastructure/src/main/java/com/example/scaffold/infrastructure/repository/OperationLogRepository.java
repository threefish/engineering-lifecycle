package com.example.scaffold.infrastructure.repository;

import com.example.scaffold.infrastructure.repository.entity.OperationLogEntity;
import com.example.scaffold.infrastructure.repository.mapper.OperationLogMapper;
import org.springframework.stereotype.Repository;

/**
 * 操作日志仓储（对齐 canjiajiu 写法）：单类继承 BaseRepository，只追加（save 来自 MP IService），不更新。
 *
 * @author scaffold
 * @date 2026-11-19
 */
@Repository
public class OperationLogRepository extends BaseRepository<OperationLogMapper, OperationLogEntity> {
}