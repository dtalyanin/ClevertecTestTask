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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ItemWriterDecoratorTest {
    @Captor
    private ArgumentCaptor<Row> rowCaptor;
    @Mock
    private PrintWriter printWriter;
    private Row mainRow;
    private ItemWriterDecorator decorator;

    @BeforeEach
    void setUp() {
        mainRow = TestRows.getMainRow();
        decorator = new ItemWriterDecorator(new ItemWriterImpl(mainRow));
    }

    @Test
    void checkWriteItemShouldWriteSingleRowWithDiscount() {
        Item item = TestItems.getPromotionalItemWithDiscount();
        List<Row> expected = List.of(TestRows.getSimpleRow(), TestRows.getDiscountRow());
        decorator.writeItem(item, printWriter);
        verify(printWriter, times(2)).println(rowCaptor.capture());
        List<Row> actual = rowCaptor.getAllValues();
        assertEquals(expected, actual);
    }

    @Test
    void checkWriteItemWithLongNameShouldWriteTwoRowsWithDiscount() {
        Item item = TestItems.getItemWithLongNameAndDiscount();
        List<Row> expected = TestRows.getSplitRowsWithDiscount();
        decorator.writeItem(item, printWriter);
        verify(printWriter, times(3)).println(rowCaptor.capture());
        List<Row> actual = rowCaptor.getAllValues();
        assertEquals(expected, actual);
    }

    @Test
    void checkWriteItemShouldThrowNotEqualsAmountColumns() {
        Item item = TestItems.getItemWithoutDiscount();
        decorator = new ItemWriterDecorator(new ItemWriterImpl(new Row(Collections.emptyList(), "")));
        FileWritingException exception = assertThrows(FileWritingException.class,
                () -> decorator.writeItem(item, printWriter));
        String expected = "Expected columns amount: 0, actual: 4";
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }
}