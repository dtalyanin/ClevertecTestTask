package ru.clevertec.task.services.item_writers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.task.exceptions.FileWritingException;
import ru.clevertec.task.generators.factories.TestItems;
import ru.clevertec.task.generators.factories.TestRows;
import ru.clevertec.task.models.Item;
import ru.clevertec.task.models.file_items.Row;

import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ItemWriterImplTest {
    @Captor
    private ArgumentCaptor<Row> rowCaptor;
    @Mock
    private PrintWriter printWriter;
    private Row mainRow;
    private ItemWriterImpl itemWriter;

    @BeforeEach
    void setUp() {
        mainRow = TestRows.getMainRow();
        itemWriter = new ItemWriterImpl(mainRow);
    }

    @Test
    void checkWriteItemShouldWriteSingleRow() {
        Item item = TestItems.getItemWithoutDiscount();
        Row expected = TestRows.getSimpleRow();
        itemWriter.writeItem(item, printWriter);
        verify(printWriter, times(1)).println(rowCaptor.capture());
        Row actual = rowCaptor.getValue();
        assertEquals(expected, actual);
    }

    @Test
    void checkWriteItemWithLongNameShouldWriteTwoRows() {
        Item item = TestItems.getItemWithLongName();
        List<Row> expected = TestRows.getSplitRows();
        itemWriter.writeItem(item, printWriter);
        verify(printWriter, times(2)).println(rowCaptor.capture());
        List<Row> actual = rowCaptor.getAllValues();
        assertEquals(expected, actual);
    }

    @Test
    void checkWriteItemShouldThrowNotEqualsAmountColumns() {
        Item item = TestItems.getItemWithoutDiscount();
        mainRow.columns().remove(0);
        FileWritingException exception = assertThrows(FileWritingException.class,
                () -> itemWriter.writeItem(item, printWriter));
        String expected = "Expected columns amount: 3, actual: 4";
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }

    @Test
    void checkGetRowShouldCreateCorrectRow() {
        List<String> columnValues = List.of("10", "Test, 1 l.", "1.00", "10.00");
        Row expected = TestRows.getSimpleRow();
        Row actual = itemWriter.getRow(columnValues);
        assertEquals(expected, actual);
    }

    @Test
    void checkGetRowShouldThrowValuesForWritingLessThanInHead() {
        FileWritingException exception = assertThrows(FileWritingException.class,
                () -> itemWriter.getRow(Collections.emptyList()));
        String expected = "Expected columns amount: 4, actual: 0";
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }

    @Test
    void checkGetRowShouldThrowValuesForWritingMoreThanInHead() {
        FileWritingException exception = assertThrows(FileWritingException.class,
                () -> itemWriter.getRow(
                        Stream.generate(() -> "test").limit(5).collect(Collectors.toList())));
        String expected = "Expected columns amount: 4, actual: 5";
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }
}