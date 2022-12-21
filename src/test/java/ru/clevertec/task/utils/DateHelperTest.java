package ru.clevertec.task.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DateHelperTest {
    private LocalDateTime dateTime = LocalDateTime.of(2022, 12, 21, 4, 5, 30);

    @Test
    void getReceiptDateTime() {
        String expected = "21-12-2022_04-05-30";
        String actual = DateHelper.getReceiptDateTime(dateTime);
        assertEquals(expected, actual);
    }

    @Test
    void getDate() {
        String expected = "21.12.2022";
        String actual = DateHelper.getDate(dateTime);
        assertEquals(expected, actual);
    }

    @Test
    void getTime() {
        String expected = "04:05:30";
        String actual = DateHelper.getTime(dateTime);
        assertEquals(expected, actual);
    }
}