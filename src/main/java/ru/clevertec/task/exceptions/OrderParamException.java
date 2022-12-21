package ru.clevertec.task.exceptions;

public class OrderParamException extends IllegalArgumentException {
    private final String wrongValue;

    public OrderParamException(String message, String wrongValue) {
        super(message);
        this.wrongValue = wrongValue;
    }

    public OrderParamException(String message) {
        this(message, null);
    }

    public String getWrongValue() {
        return wrongValue;
    }
}
