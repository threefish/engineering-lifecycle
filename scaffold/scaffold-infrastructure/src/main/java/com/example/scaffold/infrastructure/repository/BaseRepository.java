package com.example.scaffold.infrastructure.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.spring.service.IService;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;

/**
 * 仓储基类（对齐 canjiajiu 写法）：直接继承 MyBatis-Plus ServiceImpl。
 * 业务仓储继承后即具备 saveOrUpdate/getById/updateById/removeById/lambdaQuery()/page() 等能力，
 * domain 直接调用这些 MP 风格 API，不再单独定义仓储接口与实现。
 *
 * @author scaffold
 * @date 2026-11-19
 */
public abstract class BaseRepository<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements IService<T> {
}