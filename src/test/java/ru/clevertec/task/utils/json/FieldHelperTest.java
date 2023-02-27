package ru.clevertec.task.utils.json;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.clevertec.task.models.Receipt;
import ru.clevertec.task.utils.AlignFormatter;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

import static org.junit.jupiter.api.Assertions.*;
import static ru.clevertec.task.utils.json.FieldHelper.*;

class FieldHelperTest {

    @ParameterizedTest
    @ValueSource(classes = {Boolean.class, Character.class, Float.class, Integer.class, Long.class, Short.class,
            Double.class, boolean.class, char.class, float.class, int.class, long.class, short.class, double.class,
            byte.class, AlignFormatter.class})
    void isPrimitiveOrEnumShouldBeTrue(Class<?> checkedClass) {
        assertTrue(isPrimitiveOrEnum(checkedClass));
    }

    @Test
    void isPrimitiveOrEnumShouldBeFalse() {
        assertFalse(isPrimitiveOrEnum(String.class));
    }

    @Test
    void checkIsProjectClassShouldBeTrue() {
        assertTrue(isProjectClass(Receipt.class));
    }

    @Test
    void checkIsProjectClassShouldBeFalse() {
        assertFalse(isProjectClass(String.class));
    }

    @ParameterizedTest
    @ValueSource(classes = {Collection.class, AbstractCollection.class, AbstractList.class, AbstractQueue.class,
            AbstractSet.class, ArrayBlockingQueue.class, ArrayDeque.class, ArrayList.class, EnumSet.class,
            HashSet.class, LinkedBlockingDeque.class, LinkedBlockingQueue.class, LinkedHashSet.class, LinkedList.class,
            PriorityBlockingQueue.class, PriorityQueue.class})
    void checkIsCollectionShouldBeTrue(Class<?> checkedClass) {
        assertTrue(isCollection(checkedClass));
    }

    @Test
    void checkIsCollectionShouldBeFalse() {
        assertFalse(isCollection(Map.class));
    }

    @ParameterizedTest
    @ValueSource(classes = {Map.class, AbstractMap.class, WeakHashMap.class, HashMap.class,
            LinkedHashMap.class, EnumMap.class, IdentityHashMap.class})
    void checkIsMapShouldBeTrue(Class<?> checkedClass) {
        assertTrue(isMap(checkedClass));
    }

    @Test
    void checkIsMapShouldBeFalse() {
        assertFalse(isMap(List.class));
    }
}