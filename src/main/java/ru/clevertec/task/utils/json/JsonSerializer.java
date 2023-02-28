package ru.clevertec.task.utils.json;

import ru.clevertec.task.exceptions.JsonException;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class for creating JSON string from object
 */
public class JsonSerializer {
    private static final String LEFT_BRACE = "{";
    private static final String RIGHT_BRACE = "}";
    private static final String COMMA = ",";
    private static final String QUOTE = "\"";
    private static final String QUOTE_COLON = "\":";
    private static final String LEFT_BRACKET = "[";
    private static final String RIGHT_BRACKET = "]";

    /**
     * Create JSON string from object
     *
     * @param object object to parse
     * @return JSON string
     */
    public String getJsonFromObject(Object object) {
        return LEFT_BRACE + getStrObject(object, object.getClass()) + RIGHT_BRACE;
    }

    /**
     * Create object string representation
     *
     * @param object object to parse
     * @param oClass class of the specified object
     * @return object string representation
     */
    private String getStrObject(Object object, Class<?> oClass) {
        Class<?> sClass = oClass.getSuperclass();
        StringBuilder strObject = new StringBuilder();
        if (sClass != Object.class) {
            strObject.append(getStrObject(object, sClass));
        }
        List<Field> fieldsToWrite = Arrays.stream(oClass.getDeclaredFields())
                .filter(field -> !Modifier.isStatic(field.getModifiers()))
                .toList();
        return strObject.append(fieldsToWrite.stream()
                .peek(field -> field.setAccessible(true))
                .map(field -> getStrField(field, object))
                .collect(Collectors.joining(COMMA))).toString();
    }

    /**
     * Create field string representation
     *
     * @param field  field to parse
     * @param object object to parse
     * @return field string representation
     */
    private StringBuilder getStrField(Field field, Object object) {
        try {
            return getStrFieldAsKeyValue(field.getName(), field.get(object));
        } catch (IllegalAccessException e) {
            throw new JsonException("Cannot get value from field " + field.getName() + ": " + e.getMessage());
        }
    }

    /**
     * Create field string representation as field name and field value
     *
     * @param name  field name
     * @param value field value to parse
     * @return field string representation
     */
    private StringBuilder getStrFieldAsKeyValue(String name, Object value) {
        StringBuilder fieldKeyValue = new StringBuilder();
        fieldKeyValue.append(QUOTE).append(name).append(QUOTE_COLON);
        fieldKeyValue.append(getFieldValueStr(value));
        return fieldKeyValue;
    }

    /**
     * Create string representation of the field value
     *
     * @param value field value
     * @return field value string representation
     */
    private StringBuilder getFieldValueStr(Object value) {
        StringBuilder strObject = new StringBuilder();
        if (value != null) {
            Class<?> vClass = value.getClass();
            if (FieldHelper.isPrimitiveOrEnum(vClass)) {
                strObject.append(value);
            } else if (FieldHelper.isProjectClass(vClass)) {
                strObject.append(getJsonFromObject(value));
            } else if (FieldHelper.isCollection(vClass)) {
                strObject.append(getStrCollection(value));
            } else if (FieldHelper.isMap(vClass)) {
                strObject.append(getStrMap(value));
            } else if (vClass == LocalDateTime.class) {

                strObject
                        .append(QUOTE)
                        .append(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format((LocalDateTime) value))
                        .append(QUOTE);
            } else {
                strObject.append(QUOTE).append(value).append(QUOTE);
            }
        } else {
            strObject.append("null");
        }
        return strObject;
    }

    /**
     * Create collection string representation
     *
     * @param object collection to parse
     * @return collection string representation
     */
    private StringBuilder getStrCollection(Object object) {
        StringBuilder strCollection = new StringBuilder();
        strCollection.append(LEFT_BRACKET);
        Collection<?> collection = (Collection<?>) object;
        strCollection.append(collection.stream().map(this::getFieldValueStr).collect(Collectors.joining(COMMA)));
        strCollection.append(RIGHT_BRACKET);
        return strCollection;
    }

    /**
     * Create map string representation
     *
     * @param object map to parse
     * @return map string representation
     */
    private StringBuilder getStrMap(Object object) {
        StringBuilder strMap = new StringBuilder();
        strMap.append(LEFT_BRACE);
        Map<?, ?> map = (Map<?, ?>) object;
        strMap.append(map.entrySet().stream()
                .map(entry -> getStrFieldAsKeyValue(String.valueOf(entry.getKey()), entry.getValue()))
                .collect(Collectors.joining(COMMA)));
        strMap.append(RIGHT_BRACE);
        return strMap;
    }
}
