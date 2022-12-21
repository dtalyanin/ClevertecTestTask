package ru.clevertec.task.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateHelper {
    private static final String DATE_PATTERN = "dd.MM.yyyy";
    private static final String TIME_PATTERN = "HH:mm:ss";
    private static final String DATE_TIME_FILE_PATTERN = "dd-MM-yyyy_HH-mm-ss";

    public static String getReceiptDateTime(LocalDateTime dateTime) {
        return DateTimeFormatter.ofPattern(DATE_TIME_FILE_PATTERN).format(dateTime);
    }

    public static String getDate(LocalDateTime dateTime) {
        return DateTimeFormatter.ofPattern(DATE_PATTERN).format(dateTime.toLocalDate());
    }

    public static String getTime(LocalDateTime dateTime) {
        return DateTimeFormatter.ofPattern(TIME_PATTERN).format(dateTime.toLocalTime());
    }
}
