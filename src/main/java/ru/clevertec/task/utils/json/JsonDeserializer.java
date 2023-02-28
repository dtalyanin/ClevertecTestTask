package ru.clevertec.task.utils.json;

import ru.clevertec.task.exceptions.JsonException;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ru.clevertec.task.utils.json.FieldHelper.*;

/**
 * Class for creating object from JSON string
 */
public class JsonDeserializer {
    private static final char LEFT_BRACE = '{';
    private static final char RIGHT_BRACE = '}';
    private static final String COMMA = ",";
    private static final String COLON = ":";
    private static final char QUOTE = '"';
    private static final String QUOTE_COLON = "\":";
    private static final char LEFT_BRACKET = '[';
    private static final char RIGHT_BRACKET = ']';
    private static final String COMMA_QUOTE = ",\"";

    /**
     * Create new object of the specified class from JSON string
     *
     * @param oClass  object  class to create
     * @param jsonStr JSON string for parsing
     * @param <T>     generic of class to create
     * @return new object of the specified class or null if JSON is empty
     */
    public <T> T getObjectFromJson(Class<T> oClass, String jsonStr) {
        if (jsonStr != null && !jsonStr.isBlank()) {
            return getObject(oClass, jsonStr);
        } else {
            return null;
        }
    }

    /**
     * Create new object of the specified class from JSON string
     *
     * @param oClass    object  class to create
     * @param strObject JSON string for parsing
     * @param <T>       generic of class to create
     * @return new object of the specified class or null if JSON is empty
     */
    private <T> T getObject(Class<T> oClass, String strObject) {
        if (checkIsCorrectObjectStr(strObject)) {
            strObject = strObject.substring(1, strObject.length() - 1);
        } else {
            throw new JsonException("Wrong json - no braces");
        }
        Map<String, Field> fieldsToRead = getAllObjectFields(oClass);
        try {
            T object = oClass.getConstructor().newInstance();
            int firstNameIndex = 1;
            while (firstNameIndex < strObject.length()) {
                int lastNameIndex = strObject.indexOf(QUOTE_COLON, firstNameIndex);
                int firstValueIndex = lastNameIndex + 2;
                int lastValueIndex = getLastValueIndex(strObject, firstValueIndex);
                String strField = strObject.substring(firstNameIndex, lastNameIndex).toLowerCase();
                String strValue = strObject.substring(firstValueIndex, lastValueIndex);
                if (fieldsToRead.containsKey(strField)) {
                    Field field = fieldsToRead.get(strField);
                    Object fieldObject = getObjectFromField(field, field.getType(), strValue);
                    field.setAccessible(true);
                    field.set(object, fieldObject);
                }
                firstNameIndex = lastValueIndex + 2;
            }
            return object;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new JsonException("Cannot create Object from JSON: " + e.getMessage());
        }
    }

    /**
     * Check if JSON string contains braces in the first and last positions
     *
     * @param strObject string to check
     * @return true if it is correct JSON string with braces
     */
    private boolean checkIsCorrectObjectStr(String strObject) {
        return LEFT_BRACE == strObject.charAt(0) && RIGHT_BRACE == strObject.charAt(strObject.length() - 1);
    }

    /**
     * Get field class names in lower case and all non-static fields of the specified class
     *
     * @param oClass class to get field
     * @return map of field class names in lower case and all non-static fields of the specified class
     */
    private Map<String, Field> getAllObjectFields(Class<?> oClass) {
        Map<String, Field> fieldsToRead = Arrays.stream(oClass.getDeclaredFields())
                .filter(field -> !Modifier.isStatic(field.getModifiers()))
                .collect(Collectors.toMap(field -> field.getName().toLowerCase(), Function.identity()));
        Class<?> sClass = oClass.getSuperclass();
        if (sClass != Object.class) {
            fieldsToRead.putAll(getAllObjectFields(sClass));
        }
        return fieldsToRead;
    }

    /**
     * Get last index of value in string
     *
     * @param strObject       JSON string for parsing
     * @param firstValueIndex first index at which to start searching in string
     * @return last index of value in string
     */
    private int getLastValueIndex(String strObject, int firstValueIndex) {
        int lastValueIndex;
        if (strObject.charAt(firstValueIndex) == LEFT_BRACE) {
            lastValueIndex = getLastIndexOfWrappingValue(strObject, RIGHT_BRACE);
        } else if (strObject.charAt(firstValueIndex) == LEFT_BRACKET) {
            lastValueIndex = getLastIndexOfWrappingValue(strObject, RIGHT_BRACKET);
        } else {
            lastValueIndex = strObject.indexOf(COMMA_QUOTE, firstValueIndex);
            if (lastValueIndex == -1) {
                lastValueIndex = strObject.length();
            }
        }
        return lastValueIndex;
    }

    /**
     * Get last index of closing char for object or array in string
     *
     * @param strObject   JSON string for parsing
     * @param closingChar closing char
     * @return last index of closing char in string
     */
    private int getLastIndexOfWrappingValue(String strObject, char closingChar) {
        int lastValueIndex = strObject.lastIndexOf(closingChar) + 1;
        if (lastValueIndex == 0) {
            throw new JsonException("Missing closing value " + closingChar);
        }
        return lastValueIndex;
    }

    /**
     * Get object from JSON string
     *
     * @param field     field where the object should be written
     * @param fieldType class of the field where the object should be written
     * @param strValue  JSON string for parsing
     * @return created object
     * @throws NoSuchMethodException     particular method cannot be found
     * @throws InvocationTargetException an exception thrown by an invoked method or constructor
     * @throws InstantiationException    specified class object cannot be instantiated
     * @throws IllegalAccessException    method does not have access to the definition of class, field, method or constructor.
     */
    private Object getObjectFromField(Field field, Class<?> fieldType, String strValue) throws NoSuchMethodException,
            InvocationTargetException, InstantiationException, IllegalAccessException {
        Object object;
        if ("null".equals(strValue)) {
            object = null;
        } else if (isPrimitiveOrEnum(fieldType)) {
            object = getPrimitiveValue(fieldType, strValue);
        } else if (isCollection(fieldType)) {
            object = getCollection(field, fieldType, strValue);
        } else if (isMap(fieldType)) {
            object = getMap(field, strValue);
        } else if (isProjectClass(fieldType)) {
            object = getObject(fieldType, strValue);
        } else {
            strValue = strValue.substring(1, strValue.length() - 1);
            if (fieldType == LocalDateTime.class) {
                object = LocalDateTime.parse(strValue);
            } else {
                object = fieldType.getConstructor(String.class).newInstance(strValue);
            }
        }
        return object;
    }

    /**
     * Create object from primitive, wrapper class for primitive or enum
     *
     * @param primitiveClass created class
     * @param strValue       string value of primitive
     * @return created primitive wrapper or enum
     */
    private Object getPrimitiveValue(Class<?> primitiveClass, String strValue) {
        PropertyEditor editor = PropertyEditorManager.findEditor(primitiveClass);
        if (strValue.charAt(0) == QUOTE && strValue.charAt(strValue.length() - 1) == QUOTE) {
            strValue = strValue.substring(1, strValue.length() - 1);
        }
        editor.setAsText(strValue);
        return editor.getValue();
    }

    /**
     * Get collection from JSON string
     *
     * @param field     field where the object should be written
     * @param cClass    class of the field where the object should be written
     * @param strValues JSON string for parsing
     * @return created collection
     * @throws NoSuchMethodException     particular method cannot be found
     * @throws InvocationTargetException an exception thrown by an invoked method or constructor
     * @throws InstantiationException    specified class object cannot be instantiated
     * @throws IllegalAccessException    method does not have access to the definition of class, field, method or constructor.
     */
    private Collection<?> getCollection(Field field, Class<?> cClass, String strValues)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        strValues = strValues.substring(1, strValues.length() - 1);
        Class<?> generic;
        if (field.getGenericType() instanceof ParameterizedType type) {
            generic = (Class<?>) type.getActualTypeArguments()[0];
        } else {
            generic = String.class;
        }
        Collection<Object> collection = getCollectionImpl(cClass);
        //check if string contain values
        if (strValues.length() != 0) {
            for (String strValue : getSplitValues(strValues)) {
                if (generic.getClassLoader() == ClassLoader.getSystemClassLoader()) {
                    collection.add(getObject(generic, strValue));
                } else {
                    collection.add(getObjectFromField(field, generic, strValue));
                }
            }
        }
        return collection;
    }

    /**
     * Get collection implementation based on the specified class
     *
     * @param collectionClass collection class to create
     * @return collection implementation
     */
    private Collection<Object> getCollectionImpl(Class<?> collectionClass) {
        Collection<Object> collection;
        if (collectionClass == Queue.class || collectionClass == Deque.class) {
            collection = new LinkedList<>();
        } else if (collectionClass == Set.class) {
            collection = new HashSet<>();
        } else {
            collection = new ArrayList<>();
        }
        return collection;
    }

    /**
     * @param field     field where the object should be written
     * @param strValues JSON string for parsing
     * @return HashMap implementation
     * @throws NoSuchMethodException     particular method cannot be found
     * @throws InvocationTargetException an exception thrown by an invoked method or constructor
     * @throws InstantiationException    specified class object cannot be instantiated
     * @throws IllegalAccessException    method does not have access to the definition of class, field, method or constructor.
     */
    private Map<?, ?> getMap(Field field, String strValues)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        strValues = strValues.substring(1, strValues.length() - 1);
        Class<?> genericKey;
        Class<?> genericValue;
        if (field.getGenericType() instanceof ParameterizedType type) {
            genericKey = (Class<?>) type.getActualTypeArguments()[0];
            genericValue = (Class<?>) type.getActualTypeArguments()[1];
        } else {
            genericKey = String.class;
            genericValue = String.class;
        }
        Map<Object, Object> map = new HashMap<>();
        for (String strValue : strValues.split(COMMA)) {
            String[] keyValue = strValue.split(COLON);
            map.put(getObjectFromField(field, genericKey, keyValue[0]),
                    getObjectFromField(field, genericValue, keyValue[1]));
        }
        return map;
    }

    /**
     * Split string to values
     *
     * @param strValues JSON string for parsing
     * @return list of split values from string
     */
    private List<String> getSplitValues(String strValues) {
        List<String> values;
        if (strValues.charAt(0) == LEFT_BRACE) {
            values = getStrObjectsFromArray(strValues);
        } else if (strValues.charAt(0) == QUOTE) {
            values = getStringsFromArray(strValues);
        } else {
            values = List.of(strValues.split(COMMA));
        }
        return values;
    }

    /**
     * Split string into values if values is JSON object
     *
     * @param strValues JSON string for parsing
     * @return list of split values from string
     */
    private List<String> getStrObjectsFromArray(String strValues) {
        List<String> values = new ArrayList<>();
        int start = 0;
        while (start < strValues.length()) {
            int end = strValues.indexOf(RIGHT_BRACE, start) + 1;
            String value = strValues.substring(start, end);
            values.add(value);
            start = end + 1;
        }
        return values;
    }

    /**
     * Split string into values if values is strings
     *
     * @param strValues JSON string for parsing
     * @return list of split values from string
     */
    private List<String> getStringsFromArray(String strValues) {
        List<String> values = new ArrayList<>();
        int firstLetterIndex = 0;
        while (firstLetterIndex < strValues.length()) {
            int commaIndex = strValues.indexOf(COMMA, firstLetterIndex);
            if (commaIndex != -1) {
                while (strValues.charAt(firstLetterIndex) == QUOTE && strValues.charAt(commaIndex - 1) != QUOTE) {
                    commaIndex = strValues.indexOf(COMMA, commaIndex + 1);
                    //if comma is last symbol in line
                    if (commaIndex == -1) {
                        commaIndex = strValues.length();
                    }
                }
            } else {
                commaIndex = strValues.length();
            }
            values.add(strValues.substring(firstLetterIndex, commaIndex));
            firstLetterIndex = commaIndex + 1;
        }
        return values;
    }
}
