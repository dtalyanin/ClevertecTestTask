package ru.clevertec.task.exceptions;

public class OrderItemNotFoundException extends IllegalArgumentException {
    private final int missingId;

    public OrderItemNotFoundException(String message, int missingId) {
        super(message);
        this.missingId = missingId;
    }

    public int getMissingId() {
        return missingId;
    }
}
