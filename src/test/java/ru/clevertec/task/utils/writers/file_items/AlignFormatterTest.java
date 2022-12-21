package ru.clevertec.task.utils.writers.file_items;

import org.junit.jupiter.api.Test;
import ru.clevertec.task.exceptions.FileWritingException;

import static org.junit.jupiter.api.Assertions.*;

class AlignFormatterTest {

    @Test
    void getAlignedString() {
        String expected = "      text";
        String actual = AlignFormatter.RIGHT.getAlignedString("text", 10);
        assertEquals(expected, actual);

        expected = "text      ";
        actual = AlignFormatter.LEFT.getAlignedString("text", 10);
        assertEquals(expected, actual);

        expected = "   text   ";
        actual = AlignFormatter.CENTER.getAlignedString("text", 10);
        assertEquals(expected, actual);

        expected = "te";
        actual = AlignFormatter.CENTER.getAlignedString("text", 2);
        assertEquals(expected, actual);

        FileWritingException exception = assertThrows(FileWritingException.class,
                () -> AlignFormatter.CENTER.getAlignedString("text", 0));
        expected = "Wrong length for new string: 0";
        actual = exception.getMessage();
        assertEquals(expected, actual);
    }
}