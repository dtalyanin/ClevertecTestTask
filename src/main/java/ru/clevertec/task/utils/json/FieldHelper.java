package ru.clevertec.task.utils.json;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class FieldHelper {
    public static boolean isPrimitiveOrEnum(Class<?> checkedClass) {
        if (checkedClass.isPrimitive() || checkedClass.isEnum()) {
            return true;
        }
        List<Class<?>> classes = List.of(Boolean.class, Character.class, Float.class,
                Integer.class, Logger.class, Short.class, Double.class);
        return classes.contains(checkedClass);
    }

    public static boolean isProjectClass(Class<?> checkedClass) {
        return checkedClass.getClassLoader() == ClassLoader.getSystemClassLoader();
    }

    public static boolean isCollection(Class<?> chekedClass) {
        boolean isCollection = false;
        while (!isCollection && chekedClass != null) {
            isCollection = List.of(chekedClass.getInterfaces()).contains(Collection.class);
            chekedClass = chekedClass.getSuperclass();
        }
        return isCollection;
    }

    public static boolean isMap(Class<?> checkedClass) {
        return checkedClass == Map.class || List.of(checkedClass.getInterfaces()).contains(Map.class);
    }
}
