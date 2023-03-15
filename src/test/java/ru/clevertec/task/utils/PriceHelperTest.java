package ru.clevertec.task.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class PriceHelperTest {

    @Test
    void checkGetPricePercentWithRound() {
        int value = 1000;
        int percent = 10;
        int actual = PriceHelper.getPricePercentWithRound(value, percent);
        int expected = 100;
        assertEquals(expected, actual);
    }

    @Test
    void checkGetPricePercentWithRoundCeil() {
        int value = 1234;
        int percent = 17;
        int actual = PriceHelper.getPricePercentWithRound(value, percent);
        int expected = 210;
        assertEquals(expected, actual);
    }

    @Test
    void checkGetPricePercentWithRoundFloor() {
        int value = 1234;
        int percent = 16;
        int actual = PriceHelper.getPricePercentWithRound(value, percent);
        int expected = 197;
        assertEquals(expected, actual);
    }

    @Test
    void checkGetPricePercentWithRoundWhenLastNumber5() {
        int value = 211;
        int percent = 50;
        int actual = PriceHelper.getPricePercentWithRound(value, percent);
        int expected = 106;
        assertEquals(expected, actual);
    }

    @Test
    void checkGetPricePercentWithRoundNegativeNumber() {
        int value = -211;
        int percent = 50;
        int actual = PriceHelper.getPricePercentWithRound(value, percent);
        int expected = -105;
        assertEquals(expected, actual);
    }

    @ParameterizedTest(name = "{0} should convert to {1}")
    @CsvSource({"100, 1.00", "101, 1.01", "999, 9.99", "550, 5.50"})
    void getPriceRepresentation(int value, String expectedStrPrice) {
        String actual = PriceHelper.getPriceRepresentation(value);
        assertEquals(expectedStrPrice, actual);
    }
}