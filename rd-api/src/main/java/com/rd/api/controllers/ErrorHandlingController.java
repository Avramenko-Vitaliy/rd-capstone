package com.rd.api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.base.CaseFormat;
import com.rd.api.exception.APIError;
import com.rd.api.exception.ErrorMetaData;
import com.rd.api.exception.ParametersProcessingError;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@CommonsLog
@RestController
@ControllerAdvice
@AllArgsConstructor
public class ErrorHandlingController extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<APIError> responseStatusException(ResponseStatusException ex) {
        log.debug(ex.getMessage(), ex);
        return new ResponseEntity<>(new APIError(ex.getMessage()), ex.getStatusCode());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public APIError jsonProcessingException(JsonProcessingException ex) {
        log.debug(ex.getMessage(), ex);
        return new APIError(ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public APIError exception(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new APIError(ex.getMessage());
    }

    @ExceptionHandler
    public void exception(ClientAbortException ex) {
        log.trace(ex.getMessage(), ex);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public APIError exception(ValidationException ex) {
        log.debug(ex.getMessage(), ex.getCause());
        return new APIError(ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public APIError exception(DataIntegrityViolationException ex) {
        Throwable cause = ex.getCause();
        log.debug(cause.getMessage(), cause);
        return new APIError(cause.getCause().getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public APIError pathException(ParametersProcessingError ex) {
        log.debug(ex.getMessage(), ex);
        return new APIError(ex.getMessage(), ex.getErrors());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public APIError pathException(EntityNotFoundException ex) {
        log.debug(ex.getMessage(), ex);
        return new APIError(ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.debug(ex.getMessage());
        return new ResponseEntity<>(convertToReadableMessage(ex.getBindingResult(), ex.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    public static APIError convertToReadableMessage(BindingResult bindingResult, String defaultMessage) {
        if (Objects.isNull(bindingResult)) {
            return new APIError(defaultMessage);
        }

        List<ErrorMetaData> errors = bindingResult.getFieldErrors().stream()
                .map(fieldError -> {
                    String field = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldError.getField());
                    String message = switch (fieldError.getDefaultMessage()) {
                        case "must not be null", "must not be blank", "must not be empty" ->
                                String.format("Field '%s' is required", field);
                        default -> fieldError.getDefaultMessage();
                    };
                    return ErrorMetaData.builder()
                            .message(message)
                            .field(field)
                            .build();
                })
                .toList();

        if (errors.isEmpty()) {
            String message = Optional.ofNullable(bindingResult.getGlobalError())
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .orElse(defaultMessage);
            return new APIError(message);
        }

        return new APIError(errors.get(0).getMessage(), errors);
    }
}
