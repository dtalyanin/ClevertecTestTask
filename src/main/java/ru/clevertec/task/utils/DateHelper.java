package ru.clevertec.task.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateHelper {
    public static String getReceiptDateTime(LocalDateTime dateTime) {
        return DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss").format(dateTime);
    }

    public static String getDate(LocalDateTime dateTime) {
        return DateTimeFormatter.ofPattern("dd.MM.yyyy").format(dateTime.toLocalDate());
    }

    public static String getTime(LocalDateTime dateTime) {
        return DateTimeFormatter.ofPattern("HH:mm:ss").format(dateTime.toLocalTime());
    }
}
