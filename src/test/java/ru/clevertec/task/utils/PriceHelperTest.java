package ru.clevertec.task.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PriceHelperTest {

    @Test
    void getPricePercentWithRound() {
        //get with round whole number
        int value = 1000;
        int percent = 10;
        int actual = PriceHelper.getPricePercentWithRound(value, percent);
        int expected = 100;
        assertEquals(expected, actual);

        //get with round ceil
        value = 1234;
        percent = 17;
        actual = PriceHelper.getPricePercentWithRound(value, percent);
        expected = 210;
        assertEquals(expected, actual);

        //get with round floor
        value = 1234;
        percent = 16;
        actual = PriceHelper.getPricePercentWithRound(value, percent);
        expected = 197;
        assertEquals(expected, actual);

        //get with round 5
        value = 211;
        percent = 50;
        actual = PriceHelper.getPricePercentWithRound(value, percent);
        expected = 106;
        assertEquals(expected, actual);

        //round negative number
        value = -211;
        percent = 50;
        actual = PriceHelper.getPricePercentWithRound(value, percent);
        expected = -105;
        assertEquals(expected, actual);
    }

    @Test
    void getPriceRepresentation() {
        int value = 100;
        String actual = PriceHelper.getPriceRepresentation(value);
        String expected = "1.00";
        assertEquals(expected, actual);

        value = 101;
        actual = PriceHelper.getPriceRepresentation(value);
        expected = "1.01";
        assertEquals(expected, actual);

        value = 999;
        actual = PriceHelper.getPriceRepresentation(value);
        expected = "9.99";
        assertEquals(expected, actual);
    }

    @Test
    void getPriceAsDouble() {
        int value = 100;
        double actual = PriceHelper.getPriceAsDouble(value);
        double expected = 1.0;
        assertEquals(expected, actual);

        value = 101;
        actual = PriceHelper.getPriceAsDouble(value);
        expected = 1.01;
        assertEquals(expected, actual);

        value = 999;
        actual = PriceHelper.getPriceAsDouble(value);
        expected = 9.99;
        assertEquals(expected, actual);

        value = 2345;
        actual = PriceHelper.getPriceAsDouble(value);
        expected = 23.45;
        assertEquals(expected, actual);
    }
}