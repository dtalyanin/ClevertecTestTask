package ru.clevertec.task.exceptions;

/**
 * Parsing JSON error
 */
public class JsonException extends IllegalArgumentException {
    public JsonException(String message) {
        super(message);
    }
}
