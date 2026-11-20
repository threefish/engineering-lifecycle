package com.example.scaffold.facade.manage;

import com.example.scaffold.common.annotation.ManageApi;
import com.example.scaffold.common.annotation.RequiresPermission;
import com.example.scaffold.common.constant.RoutePrefix;
import com.example.scaffold.common.log.OperateType;
import com.example.scaffold.common.log.OperationLog;
import com.example.scaffold.common.page.PageResult;
import com.example.scaffold.common.result.Result;
import com.example.scaffold.domain.service.NoteService;
import com.example.scaffold.model.enums.NoteStatus;
import com.example.scaffold.model.note.NoteVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 备忘录管理门面：只做参数接收、校验与转发，零业务逻辑、零事务、不注入 Repository。
 *
 * @author scaffold
 * @date 2026-11-19
 */
@Tag(name = "备忘录管理")
@RestController
@RequestMapping(RoutePrefix.MANAGE_API + "/v1/note")
@RequiredArgsConstructor
@ManageApi
public class NoteManageController {

    private final NoteService noteService;

    @Operation(summary = "保存备忘录")
    @PostMapping("/save")
    @RequiresPermission("opt_note_save")
    @OperationLog(operateType = OperateType.SAVE_OR_UPDATE, content = "保存备忘录", keyword = "备忘录")
    public Result<String> save(@RequestBody @Validated NoteVO.SaveRequest request) {
        return Result.success(String.valueOf(noteService.save(request)));
    }

    @Operation(summary = "备忘录详情")
    @PostMapping("/detail")
    @RequiresPermission("opt_note_list")
    @OperationLog(operateType = OperateType.QUERY, content = "查询备忘录详情")
    public Result<NoteVO.Detail> detail(@RequestBody @Validated NoteVO.IdRequest request) {
        return Result.success(noteService.detail(request));
    }

    @Operation(summary = "按条件分页查询备忘录")
    @PostMapping("/pagingQueryByCondition")
    @RequiresPermission("opt_note_list")
    @OperationLog(operateType = OperateType.QUERY, content = "分页查询备忘录")
    public Result<PageResult<NoteVO.Item>> pagingQueryByCondition(@RequestBody @Validated NoteVO.PageQuery query) {
        return Result.success(noteService.pagingQueryByCondition(query));
    }

    @Operation(summary = "启用或停用备忘录")
    @PostMapping("/enableOrDisable")
    @RequiresPermission("opt_note_save")
    @OperationLog(operateType = OperateType.ENABLE_OR_DISABLE, content = "启用或停用备忘录")
    public Result<Void> enableOrDisable(@RequestBody @Validated NoteVO.EnableOrDisableRequest request) {
        noteService.enableOrDisable(request);
        return Result.success();
    }

    @Operation(summary = "删除备忘录")
    @PostMapping("/delete")
    @RequiresPermission("opt_note_delete")
    @OperationLog(operateType = OperateType.DELETE, content = "删除备忘录")
    public Result<Void> delete(@RequestBody @Validated NoteVO.IdRequest request) {
        noteService.delete(request);
        return Result.success();
    }

    @Operation(summary = "备忘录状态枚举下拉")
    @GetMapping("/statusOptions")
    public Result<List<NoteVO.Option>> statusOptions() {
        List<NoteVO.Option> options = Arrays.stream(NoteStatus.values())
                .map(s -> new NoteVO.Option(s.getLabel(), s.name()))
                .collect(Collectors.toList());
        return Result.success(options);
    }
}