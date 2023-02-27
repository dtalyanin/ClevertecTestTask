package ru.clevertec.task.utils.json;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class FieldHelper {
    public static boolean isPrimitiveOrEnum(Class<?> checkedClass) {
        if (checkedClass.isPrimitive() || checkedClass.isEnum()) {
            return true;
        }
        List<Class<?>> classes = List.of(Boolean.class, Character.class, Float.class,
                Integer.class, Long.class, Short.class, Double.class, Byte.class);
        return classes.contains(checkedClass);
    }

    public static boolean isProjectClass(Class<?> checkedClass) {
        return checkedClass.getClassLoader() == ClassLoader.getSystemClassLoader();
    }

    public static boolean isCollection(Class<?> chekedClass) {
        return checkIsImplementClass(chekedClass, Collection.class);
    }

    public static boolean isMap(Class<?> checkedClass) {
        return checkIsImplementClass(checkedClass, Map.class);
    }

    private static boolean checkIsImplementClass(Class<?> chekedClass, Class<?> mustImplClass) {
        boolean containt = chekedClass == mustImplClass;
        while (!containt && chekedClass != null) {
            containt = List.of(chekedClass.getInterfaces()).contains(mustImplClass);
            chekedClass = chekedClass.getSuperclass();
        }
        return containt;
    }
}
