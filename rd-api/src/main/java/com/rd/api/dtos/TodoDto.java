package com.rd.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

@Value
public class TodoDto implements Serializable {

    Integer id;

    @NotBlank
    String name;

    @NotNull
    LocalDateTime date;

    String description;
}
