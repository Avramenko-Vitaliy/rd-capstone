package com.rd.api.dtos;

import com.rd.api.entities.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoDto {

    @Schema(accessMode = READ_ONLY)
    private Integer id;

    @NotBlank
    @Size(max = 80)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime date;

    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Status.Type status;

    @Size(max = 255)
    @Schema(nullable = true, maxLength = 255)
    private String description;
}
