package ru.clevertec.task.utils.json;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Class to check what the specified class belongs to
 */
public class FieldHelper {
    /**
     * Check if the specified class is primitive, wrapper class for primitive or enum
     *
     * @param checkedClass class to check
     * @return true if primitive or enum
     */
    public static boolean isPrimitiveOrEnum(Class<?> checkedClass) {
        if (checkedClass.isPrimitive() || checkedClass.isEnum()) {
            return true;
        }
        List<Class<?>> classes = List.of(Boolean.class, Character.class, Float.class,
                Integer.class, Long.class, Short.class, Double.class, Byte.class);
        return classes.contains(checkedClass);
    }

    /**
     * Check if the specified class is from project package
     *
     * @param checkedClass class to check
     * @return true if from project package
     */
    public static boolean isProjectClass(Class<?> checkedClass) {
        return checkedClass.getClassLoader() == ClassLoader.getSystemClassLoader();
    }

    /**
     * Check if the specified class is from Collections
     *
     * @param checkedClass class to check
     * @return true if from Collections
     */
    public static boolean isCollection(Class<?> checkedClass) {
        return checkIsImplementClass(checkedClass, Collection.class);
    }

    /**
     * Check if the specified class is from Map
     *
     * @param checkedClass class to check
     * @return true if from Map
     */
    public static boolean isMap(Class<?> checkedClass) {
        return checkIsImplementClass(checkedClass, Map.class);
    }

    /**
     * Check if class is in the inheritance tree of the specified class
     *
     * @param checkedClass  class to check
     * @param mustImplClass inheritance tree of the specified class
     * @return true if from Map
     */
    private static boolean checkIsImplementClass(Class<?> checkedClass, Class<?> mustImplClass) {
        boolean contain = checkedClass == mustImplClass;
        while (!contain && checkedClass != null) {
            contain = List.of(checkedClass.getInterfaces()).contains(mustImplClass);
            checkedClass = checkedClass.getSuperclass();
        }
        return contain;
    }
}
