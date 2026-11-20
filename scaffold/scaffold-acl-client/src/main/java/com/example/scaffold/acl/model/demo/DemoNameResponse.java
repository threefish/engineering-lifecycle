package com.example.scaffold.acl.model.demo;

import lombok.Data;

/**
 * 外部演示系统响应模型（防腐层外部报文，与内部 VO 隔离）。
 *
 * @author scaffold
 * @date 2026-11-19
 */
@Data
public class DemoNameResponse {

    /** 外部是否成功 */
    private boolean success;

    /** 外部返回码 */
    private String code;

    /** 外部返回文案 */
    private String msg;
}