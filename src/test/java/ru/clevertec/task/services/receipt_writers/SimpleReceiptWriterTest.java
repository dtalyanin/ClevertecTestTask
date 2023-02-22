package ru.clevertec.task.services.receipt_writers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.clevertec.task.generators.factories.TestColumns;
import ru.clevertec.task.generators.factories.TestItems;
import ru.clevertec.task.generators.factories.TestReceipts;
import ru.clevertec.task.generators.factories.TestRows;
import ru.clevertec.task.models.Item;
import ru.clevertec.task.models.Receipt;
import ru.clevertec.task.models.file_items.Column;
import ru.clevertec.task.models.file_items.Row;
import ru.clevertec.task.utils.AlignFormatter;
import ru.clevertec.task.utils.factories.ItemWriterFactory;

import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static ru.clevertec.task.generators.factories.TestItems.*;
import static ru.clevertec.task.generators.factories.TestReceipts.*;

@SpringBootTest
class SimpleReceiptWriterTest {
    @Captor
    private ArgumentCaptor<Row> rowCaptor;
    @Captor
    private ArgumentCaptor<Column> columnCaptor;
    @Captor
    private ArgumentCaptor<String> strCaptor;
    @Mock
    private PrintWriter printWriter;
    @Spy
    private ItemWriterFactory factory;
    private SimpleReceiptWriter receiptWriter;

    @BeforeEach
    void setUp() {
        receiptWriter = new SimpleReceiptWriter(getSimpleReceipt(), printWriter, factory);
    }

    @Test
    void checkWriteHeaderShouldWrite3Columns() {
        receiptWriter.writeHeader();
        verify(printWriter, times(3)).println(columnCaptor.capture());
        List<Column> expected = List.of(TestColumns.getHeader(), TestColumns.getDate(), TestColumns.getTime());
        List<Column> actual = columnCaptor.getAllValues();
        assertEquals(expected, actual);
    }

    @Test
    void checkWriteLineShouldWriteLineDelimiter() {
        receiptWriter.writeLine();
        verify(printWriter, times(1)).println(strCaptor.capture());
        String expected = "_".repeat(receiptWriter.maxRowLength);
        String actual = strCaptor.getValue();
        assertEquals(expected, actual);
    }

    @Test
    void checkWriteItemsShouldWriteWithoutDiscount() {
        receiptWriter.writeItems();
        verify(printWriter, times(1)).println(rowCaptor.capture());
        verify(factory, times(1)).getItemWriter(any(Item.class), any(Row.class));
        Row expected = TestRows.getSimpleRow();
        Row actual = rowCaptor.getValue();
        assertEquals(expected, actual);
    }

    @Test
    void checkWriteItemsShouldWriteWithDiscount() {
        //add item with discount
        receiptWriter.receipt.items().add(getPromotionalItemWithDiscount());

        receiptWriter.writeItems();
        verify(printWriter, times(3)).println(rowCaptor.capture());
        verify(factory, times(2)).getItemWriter(any(Item.class), any(Row.class));
        List<Row> expected = List.of(TestRows.getSimpleRow(), TestRows.getSimpleRow(), TestRows.getDiscountRow());
        List<Row> actual = rowCaptor.getAllValues();
        assertEquals(expected, actual);
    }

    @Test
    void checkWriteFooterShouldWriteSingleRow() {
        receiptWriter.writeFooter();
        verify(printWriter, times(1)).println(rowCaptor.capture());
        Row expected = TestRows.getTotalPriceRow();
        Row actual = rowCaptor.getValue();
        assertEquals(expected, actual);
    }
}