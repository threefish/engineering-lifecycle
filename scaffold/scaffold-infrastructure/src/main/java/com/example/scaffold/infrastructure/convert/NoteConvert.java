package com.example.scaffold.infrastructure.convert;

import com.example.scaffold.infrastructure.repository.entity.NoteEntity;
import com.example.scaffold.model.enums.NoteStatus;
import com.example.scaffold.model.note.NoteVO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * 备忘录 Entity ↔ VO 转换：MapStruct + INSTANCE 单例；禁用 builder 走 Lombok setter；状态文案用 @Named 渲染。
 *
 * @author scaffold
 * @date 2026-11-19
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        builder = @Builder(disableBuilder = true))
public interface NoteConvert {

    NoteConvert INSTANCE = Mappers.getMapper(NoteConvert.class);

    @Mapping(target = "statusLabel", source = "status", qualifiedByName = "statusLabel")
    NoteVO.Item toItem(NoteEntity entity);

    @Mapping(target = "statusLabel", source = "status", qualifiedByName = "statusLabel")
    NoteVO.Detail toDetail(NoteEntity entity);

    NoteEntity toEntity(NoteVO.SaveRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createUser", ignore = true)
    @Mapping(target = "createUserDesc", ignore = true)
    @Mapping(target = "createDateTime", ignore = true)
    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateUserDesc", ignore = true)
    @Mapping(target = "updateDateTime", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    void updateEntity(NoteVO.SaveRequest request, @MappingTarget NoteEntity entity);

    @Named("statusLabel")
    default String statusLabel(NoteStatus status) {
        return status == null ? null : status.getLabel();
    }
}