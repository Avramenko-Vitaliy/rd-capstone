package com.rd.api.controllers.params.pagination;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.*;
import org.springdoc.core.annotations.ParameterObject;

@Data
@ParameterObject
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SortingPageParams extends PageParams {

    @JsonProperty("asc")
    @Parameter(description = "Sorts in ascending(true) or descending(false) order. By default - true.")
    private boolean asc = true;

    private String sort;

}
