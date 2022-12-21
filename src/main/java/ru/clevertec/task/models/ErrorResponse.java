package ru.clevertec.task.models;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private final Object wrongValue;
    private final String message;

    public ErrorResponse(String message) {
        this(null, message);
    }

    public ErrorResponse(Object wrongValue, String message) {
        this.wrongValue = wrongValue;
        this.message = message;
    }

    public Object getWrongValue() {
        return wrongValue;
    }

    public String getMessage() {
        return message;
    }
}
