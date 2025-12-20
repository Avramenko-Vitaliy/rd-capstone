package com.todo.api.controllers.params;

import com.todo.api.controllers.params.pagination.SearchSortingPageParams;
import com.todo.api.entities.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springdoc.core.annotations.ParameterObject;

import java.time.LocalDateTime;

@Data
@ParameterObject
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TodoParams extends SearchSortingPageParams {

    private Status.Type status;
    private LocalDateTime from;
    private LocalDateTime to;
}
