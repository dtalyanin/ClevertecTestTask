package ru.clevertec.task.services.receipt_writers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.clevertec.task.generators.factories.TestColumns;
import ru.clevertec.task.generators.factories.TestItems;
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

@SpringBootTest
class SimpleReceiptWriterTest {
    @Captor
    private ArgumentCaptor<Row> rowCaptor;
    @Captor
    private ArgumentCaptor<Column> columnCaptor;
    @Captor
    private ArgumentCaptor<Item> itemCaptor;
    @Captor
    private ArgumentCaptor<String> strCaptor;
    @Mock
    private PrintWriter printWriter;
    @Spy
    ItemWriterFactory factory;
    private Receipt receipt;
    private SimpleReceiptWriter receiptWriter;

    @BeforeEach
    void setUp() {
        receipt = Receipt.builder()
                .item(TestItems.getItemWithoutDiscount())
                .dateTime(LocalDateTime.of(2022, 2, 22, 06, 0 ,0))
                .build();
        receiptWriter = new SimpleReceiptWriter(receipt, printWriter, factory);
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
        //set item with discount
        receiptWriter.receipt.items().set(0, TestItems.getPromotionalItemWithDiscount());

        receiptWriter.writeItems();
        verify(printWriter, times(2)).println(rowCaptor.capture());
        verify(factory, times(1)).getItemWriter(any(Item.class), any(Row.class));
        List<Row> expected = List.of(TestRows.getSimpleRow(), TestRows.getDiscountRow());
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