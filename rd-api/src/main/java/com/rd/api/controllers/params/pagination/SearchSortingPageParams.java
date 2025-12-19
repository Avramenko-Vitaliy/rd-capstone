package com.rd.api.controllers.params.pagination;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springdoc.core.annotations.ParameterObject;

@Data
@ParameterObject
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SearchSortingPageParams extends SortingPageParams {

    private String search;
}
