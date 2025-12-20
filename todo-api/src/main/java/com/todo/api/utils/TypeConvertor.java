package com.todo.api.utils;

import lombok.experimental.UtilityClass;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Convert enum's entity to enum and vise versa.
 */
@UtilityClass
public class TypeConvertor {

    public <T extends Enum<T>> T toDto(ApiKey<T> type) {
        return Optional.ofNullable(type).map(ApiKey::getApiKey).orElse(null);
    }

    public <T> T toEntity(TypeCreator<T> type) {
        return Optional.ofNullable(type).map(TypeCreator::getInstance).orElse(null);
    }

    public <T extends Enum<T>> List<T> toDto(List<? extends ApiKey<T>> types) {
        if (CollectionUtils.isEmpty(types)) {
            return Collections.emptyList();
        }
        return types.stream().map(TypeConvertor::toDto).toList();
    }
}
