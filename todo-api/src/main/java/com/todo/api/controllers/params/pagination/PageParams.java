package com.todo.api.controllers.params.pagination;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;

@Data
@ParameterObject
@NoArgsConstructor
@AllArgsConstructor
public class PageParams {

    @PositiveOrZero
    @JsonProperty("offset")
    @Parameter(
            schema = @Schema(minimum = "0"),
            description = "OFFSET indicates to skip the specified number of rows. By default, OFFSET = 0 is equivalent to the absence of indication of OFFSET. Must be positive or zero."
    )
    private int offset = 0;

    @Positive
    @JsonProperty("limit")
    @Parameter(
            schema = @Schema(minimum = "1"),
            description = "If the number LIMIT is specified, the result is no more than the specified number of rows (less can be if the query itself produced fewer lines). By default, LIMIT = 15. Must be positive."
    )
    private int limit = 15;
}
