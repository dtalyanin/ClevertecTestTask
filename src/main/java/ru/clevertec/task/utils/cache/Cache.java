package ru.clevertec.task.utils.cache;

public interface Cache<T> {

    T get(int key);
    void put(int key, T value);
    void delete(int key);
}
