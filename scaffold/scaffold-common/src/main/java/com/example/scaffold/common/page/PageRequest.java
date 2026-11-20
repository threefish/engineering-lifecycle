package com.example.scaffold.common.page;

import jakarta.validation.constraints.Min;
import lombok.Data;

/**
 * 分页请求基类：业务分页查询继承它（见 model 层 XxxVO.PageQuery）。
 *
 * @author scaffold
 * @date 2026-11-19
 */
@Data
public class PageRequest {

    /** 页码，从 1 开始 */
    @Min(value = 1, message = "页码不能小于1")
    private int pageNo = 1;

    /** 每页条数 */
    @Min(value = 1, message = "每页条数不能小于1")
    private int pageSize = 10;
}