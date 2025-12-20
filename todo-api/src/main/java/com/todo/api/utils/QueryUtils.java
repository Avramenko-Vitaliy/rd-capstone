package com.todo.api.utils;

import com.todo.api.controllers.params.pagination.SortingPageParams;
import com.todo.api.repositories.OffsetBasedPageRequest;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class QueryUtils {

    public static Pageable buildPageRequest(SortingPageParams params, Map<String, String[]> mappedSort, String... defaultSort) {
        String[] sortFields;
        if (StringUtils.isBlank(params.getSort())) {
            sortFields = defaultSort;
        } else {
            sortFields = mappedSort.getOrDefault(params.getSort(), defaultSort);
        }
        List<Sort.Order> orders = Stream.of(sortFields)
                .map(sortField -> params.isAsc()
                        ? Sort.Order.asc(sortField).nullsLast()
                        : Sort.Order.desc(sortField).nullsFirst()
                )
                .collect(Collectors.toList());
        Sort sort = Sort.by(orders).and(Sort.by("id"));

        return OffsetBasedPageRequest.builder()
                .limit(params.getLimit())
                .offset(params.getOffset())
                .sort(sort)
                .build();
    }

    public static String formatLike(String searchStr) {
        return StringUtils.isBlank(searchStr) ? "%" : String.format("%%%s%%", searchStr.toLowerCase());
    }
}
