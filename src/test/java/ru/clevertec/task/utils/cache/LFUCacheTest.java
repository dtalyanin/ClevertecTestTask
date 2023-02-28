package ru.clevertec.task.utils.cache;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.task.utils.cache.impl.LFUCache;

class LFUCacheTest {
    private LFUCache<Integer> cache;

    @BeforeEach
    void setUp() {
        cache = new LFUCache<>(3);
    }

    @Test
    void checkCreatingCacheShouldThrowExceptionWithWrongCapacity() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new LFUCache<Integer>(0));
        String expectedMessage = "Capacity for cache should be more than 0, but was 0";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void checkGetShouldReturnValueIfKeyPresent() {
        cache.put(1, 111);
        assertEquals(111, cache.get(1));
    }

    @Test
    void checkGetShouldReturnNullIfKeyNotPresent() {
        cache.put(1, 111);
        assertNull(cache.get(2));
    }

    @Test
    void checkGetShouldReturnNullIfCacheEmpty() {
        assertNull(cache.get(2));
    }

    @Test
    void checkPutShouldAddAllValuesWithinCapacity() {
        cache.put(1, 111);
        cache.put(2, 222);
        cache.put(3, 333);
        assertEquals(111, cache.get(1));
        assertEquals(222, cache.get(2));
        assertEquals(333, cache.get(3));
    }

    @Test
    void checkPutShouldAddValuesAndRemoveLessUsedByFIFO() {
        //should remove 1, because all has equal using, but 1 was added first
        cache.put(1, 111);
        cache.put(2, 222);
        cache.put(3, 333);
        cache.put(4, 444);
        assertNull(cache.get(1));
        assertEquals(222, cache.get(2));
        assertEquals(333, cache.get(3));
        assertEquals(444, cache.get(4));
    }

    @Test
    void checkPutShouldAddValuesAndUpdateFrequencyWhenUpdateKeyValues() {
        //should remove 3, because 1 and 2 more used by updating values
        cache.put(1, 111);
        cache.put(2, 222);
        cache.put(3, 333);
        cache.put(1, 1111);
        cache.put(2, 2222);
        cache.put(1, 11111);
        cache.put(2, 22222);
        cache.put(3, 3333);
        cache.put(4, 444);
        assertNull(cache.get(3));
        assertEquals(22222, cache.get(2));
        assertEquals(11111, cache.get(1));
        assertEquals(444, cache.get(4));
    }

    @Test
    void checkPutShouldAddValuesAndUpdateFrequencyWhenCallGetMethod() {
        //should remove 3, because 1 and 2 frequencies updated when call method get
        cache.put(1, 111);
        cache.put(2, 222);
        cache.get(1);
        cache.get(2);
        cache.put(3, 333);
        cache.put(4, 444);
        assertNull(cache.get(3));
        assertEquals(111, cache.get(1));
        assertEquals(222, cache.get(2));
        assertEquals(444, cache.get(4));
    }

    @Test
    void checkDeleteShouldDeleteExistingKey() {
        cache.put(1, 111);
        cache.put(2, 222);
        cache.put(3, 333);
        cache.delete(2);
        assertEquals(111, cache.get(1));
        assertEquals(333, cache.get(3));
        assertNull(cache.get(2));
    }

    @Test
    void checkDeleteShouldDoNothingWithNotPresentKey() {
        cache.put(1, 111);
        cache.put(2, 222);
        cache.put(3, 333);
        assertDoesNotThrow(() -> cache.delete(4));
        assertEquals(111, cache.get(1));
        assertEquals(222, cache.get(2));
        assertEquals(333, cache.get(3));
    }
}