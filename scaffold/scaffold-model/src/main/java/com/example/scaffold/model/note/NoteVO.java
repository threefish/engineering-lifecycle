package com.example.scaffold.model.note;

import com.example.scaffold.common.page.PageRequest;
import com.example.scaffold.model.enums.NoteStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 备忘录视图模型：同一功能的请求/响应/分页/子项聚合为嵌套类，对外以 NoteVO.Item / NoteVO.SaveRequest 等引用，防 VO/DTO 爆炸。
 *
 * @author scaffold
 * @date 2026-11-19
 */
@Schema(description = "备忘录-视图模型")
public class NoteVO {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "列表展示项")
    public static class Item {

        @Schema(description = "主键")
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        private Long id;

        @Schema(description = "标题")
        private String title;

        @Schema(description = "状态")
        private NoteStatus status;

        @Schema(description = "状态中文文案")
        private String statusLabel;

        @Schema(description = "标签")
        private List<String> tags;

        @Schema(description = "创建时间")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private LocalDateTime createDateTime;

        @Schema(description = "更新时间")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private LocalDateTime updateDateTime;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "详情")
    public static class Detail {

        @Schema(description = "主键")
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        private Long id;

        @Schema(description = "标题")
        private String title;

        @Schema(description = "正文")
        private String content;

        @Schema(description = "标签")
        private List<String> tags;

        @Schema(description = "状态")
        private NoteStatus status;

        @Schema(description = "状态中文文案")
        private String statusLabel;

        @Schema(description = "创建人")
        private String createUser;

        @Schema(description = "创建时间")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private LocalDateTime createDateTime;

        @Schema(description = "修改人")
        private String updateUser;

        @Schema(description = "更新时间")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private LocalDateTime updateDateTime;
    }

    @Data
    @Schema(description = "保存请求")
    public static class SaveRequest {

        @Schema(description = "主键，新增为空")
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        private Long id;

        @Schema(description = "标题")
        @NotBlank(message = "标题不能为空")
        @Size(max = 100, message = "标题长度不能超过100个字符")
        private String title;

        @Schema(description = "正文")
        private String content;

        @Schema(description = "标签")
        private List<String> tags;

        @Schema(description = "状态")
        private NoteStatus status;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    @Schema(description = "分页查询请求")
    public static class PageQuery extends PageRequest {

        @Schema(description = "标题（模糊）")
        private String title;

        @Schema(description = "状态")
        private NoteStatus status;
    }

    @Data
    @Schema(description = "主键请求")
    public static class IdRequest {

        @Schema(description = "主键")
        @NotNull(message = "主键不能为空")
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        private Long id;
    }

    @Data
    @Schema(description = "启用或停用请求")
    public static class EnableOrDisableRequest {

        @Schema(description = "主键")
        @NotNull(message = "主键不能为空")
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        private Long id;

        @Schema(description = "目标状态")
        @NotNull(message = "状态不能为空")
        private NoteStatus status;
    }

    /** 枚举下拉项：极小不可变值对象用 record */
    @Schema(description = "下拉选项")
    public record Option(String label, String value) {
    }
}