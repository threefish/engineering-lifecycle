package com.example.scaffold.domain.service;

import com.example.scaffold.common.page.PageResult;
import com.example.scaffold.model.note.NoteVO;

/**
 * 备忘录业务接口：Controller 只注入接口。
 *
 * @author scaffold
 * @date 2026-11-19
 */
public interface NoteService {

    /** 新增或更新，返回主键 */
    Long save(NoteVO.SaveRequest request);

    /** 详情 */
    NoteVO.Detail detail(NoteVO.IdRequest request);

    /** 按条件分页查询 */
    PageResult<NoteVO.Item> pagingQueryByCondition(NoteVO.PageQuery query);

    /** 启用或停用 */
    void enableOrDisable(NoteVO.EnableOrDisableRequest request);

    /** 逻辑删除 */
    void delete(NoteVO.IdRequest request);
}