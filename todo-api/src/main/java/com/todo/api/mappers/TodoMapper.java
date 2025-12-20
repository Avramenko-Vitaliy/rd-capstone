package com.todo.api.mappers;

import com.todo.api.dtos.TodoDto;
import com.todo.api.entities.Todo;
import com.todo.api.utils.TypeConvertor;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, imports = TypeConvertor.class)
public interface TodoMapper {

    @Mapping(target = "status", expression = "java(TypeConvertor.toEntity(dto.getStatus()))")
    Todo toEntity(TodoDto dto);

    @Mapping(target = "status", expression = "java(TypeConvertor.toDto(entity.getStatus()))")
    TodoDto toDto(Todo entity);

    @Mapping(target = "status", expression = "java(TypeConvertor.toEntity(dto.getStatus()))")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Todo partialUpdate(TodoDto dto, @MappingTarget Todo entity);
}
