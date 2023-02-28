package ru.clevertec.task.exceptions;

/**
 * Creating cache implementation error
 */
public class CacheException extends IllegalArgumentException {
    public CacheException(String s) {
        super(s);
    }
}
