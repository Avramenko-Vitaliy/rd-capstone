package com.rd.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pagination<T> {

    private List<T> data = Collections.emptyList();
    private Integer total = 0;
}
