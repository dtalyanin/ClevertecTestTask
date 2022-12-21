package ru.clevertec.task.exceptions;

public class FileWritingException extends RuntimeException {
    public FileWritingException(String message) {
        super(message);
    }
}
