package ru.clevertec.task.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import ru.clevertec.task.exceptions.FileWritingException;

import static org.junit.jupiter.api.Assertions.*;

class AlignFormatterTest {
    private String str = "text";

    @ParameterizedTest
    @CsvSource(value = {"10,      text", "5, text", "4,text"}, ignoreLeadingAndTrailingWhitespace = false)
    void checkGetAlignedStringFromRightFormatter(int value, String expected) {
        String actual = AlignFormatter.RIGHT.getAlignedString(str, value);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource(value = {"10,text      ", "5,text ", "4,text"}, ignoreLeadingAndTrailingWhitespace = false)
    void checkGetAlignedStringFromLeftFormatter(int value, String expected) {
        String actual = AlignFormatter.LEFT.getAlignedString(str, value);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource(value = {"10,   text   ", "5,text ", "4,text"}, ignoreLeadingAndTrailingWhitespace = false)
    void checkGetAlignedStringFromCenterFormatter(int value, String expected) {
        String actual = AlignFormatter.CENTER.getAlignedString(str, value);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @EnumSource(AlignFormatter.class)
    void checkGetAlignedStringShouldReturnSmallerStr(AlignFormatter formatter) {
        String expected = "te";
        String actual = formatter.getAlignedString(str, 2);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @EnumSource(AlignFormatter.class)
    void checkGetAlignedStringShouldThrowWrongLength(AlignFormatter formatter) {
        FileWritingException exception = assertThrows(FileWritingException.class,
                () -> formatter.getAlignedString(str, 0));

        String expected = "Wrong length for new string: 0";
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }
}