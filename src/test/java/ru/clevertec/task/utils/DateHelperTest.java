package ru.clevertec.task.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DateHelperTest {
    private LocalDateTime dateTime;
    private LocalDateTime dateTimeWithNumbersLessThanTen;

    @BeforeEach
    void setUp() {
        dateTime = LocalDateTime.of(2022, 12, 22, 14, 30, 30);
        dateTimeWithNumbersLessThanTen = LocalDateTime.of(2022, 6, 1, 4, 5, 0);
    }

    @Test
    void checkGetReceiptDateTime() {
        String expected = "22-12-2022_14-30-30";
        String actual = DateHelper.getReceiptDateTime(dateTime);
        assertEquals(expected, actual);
    }

    @Test
    void checkGetReceiptDateTimeWithNumbersLessThanTen() {
        String expected = "01-06-2022_04-05-00";
        String actual = DateHelper.getReceiptDateTime(dateTimeWithNumbersLessThanTen);
        assertEquals(expected, actual);
    }

    @Test
    void checkGetDate() {
        String expected = "22.12.2022";
        String actual = DateHelper.getDate(dateTime);
        assertEquals(expected, actual);
    }

    @Test
    void checkGetDateWithNumbersLessThanTen() {
        String expected = "01.06.2022";
        String actual = DateHelper.getDate(dateTimeWithNumbersLessThanTen);
        assertEquals(expected, actual);
    }

    @Test
    void checkGetTime() {
        String expected = "14:30:30";
        String actual = DateHelper.getTime(dateTime);
        assertEquals(expected, actual);
    }

    @Test
    void checkGetTimeWithNumbersLessThanTen() {
        String expected = "04:05:00";
        String actual = DateHelper.getTime(dateTimeWithNumbersLessThanTen);
        assertEquals(expected, actual);
    }
}