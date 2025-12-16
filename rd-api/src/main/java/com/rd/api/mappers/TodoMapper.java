package com.rd.api.mappers;

import com.rd.api.dtos.TodoDto;
import com.rd.api.entities.Todo;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TodoMapper {
    Todo toEntity(TodoDto todoDto);

    TodoDto toDto(Todo todo);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Todo partialUpdate(TodoDto todoDto, @MappingTarget Todo todo);
}
