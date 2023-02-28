package ru.clevertec.task.utils.factories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import ru.clevertec.task.utils.cache.impl.LFUCache;
import ru.clevertec.task.utils.cache.impl.LRUCache;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CacheFactoryTest {
    @Autowired
    private CacheFactory factory;

    @Test
    void checkGetCacheImplementationShouldBeLFU() {
        ReflectionTestUtils.setField(factory, "cacheChoice", "lfu");
        Class<?> expected = LFUCache.class;
        Class<?> actual = factory.getCacheImplementation().getClass();
        assertEquals(expected, actual);
    }

    @Test
    void checkGetCacheImplementationShouldBeLRU() {
        ReflectionTestUtils.setField(factory, "cacheChoice", "lru");
        Class<?> expected = LRUCache.class;
        Class<?> actual = factory.getCacheImplementation().getClass();
        assertEquals(expected, actual);
    }

    @Test
    void checkGetCacheImplementationShouldThrowExceptionWrongValue() {
        ReflectionTestUtils.setField(factory, "cacheChoice", "lruu");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> factory.getCacheImplementation());
        String expected = "Wrong value for cache implementation: lruu";
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }

    @Test
    void checkGetCacheImplementationShouldThrowExceptionNullValue() {
        ReflectionTestUtils.setField(factory, "cacheChoice", null);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> factory.getCacheImplementation());
        String expected = "No value for cache implementation";
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }
}