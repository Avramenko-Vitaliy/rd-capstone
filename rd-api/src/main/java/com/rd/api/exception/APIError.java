package com.rd.api.exception;

import lombok.Data;
import lombok.extern.apachecommons.CommonsLog;

import java.util.List;

@Data
@CommonsLog
public class APIError {

    private String message;
    private List<ErrorMetaData> errors;

    public APIError(String message) {
        this.message = message;
    }

    public APIError(String message, List<ErrorMetaData> errors) {
        this.message = message;
        this.errors = errors;
    }
}
