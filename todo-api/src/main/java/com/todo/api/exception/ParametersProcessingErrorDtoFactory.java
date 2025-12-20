package com.todo.api.exception;

import com.google.common.collect.Lists;
import com.todo.api.services.MessagesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ParametersProcessingErrorDtoFactory {

    private final MessagesService ms;

    public ParametersProcessingError createError(String field, String messageTemplate, Object... args) {
        String message = ms.getMessage(messageTemplate, args);
        return new ParametersProcessingError(message, Lists.newArrayList(buildErrorMetaData(field, message)));
    }

    public ErrorMetaData buildErrorMetaData(String field, String message) {
        return ErrorMetaData.builder()
                .field(field)
                .message(message)
                .build();
    }
}
