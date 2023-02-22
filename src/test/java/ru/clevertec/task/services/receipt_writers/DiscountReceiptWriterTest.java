package ru.clevertec.task.services.receipt_writers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import ru.clevertec.task.generators.factories.TestReceipts;
import ru.clevertec.task.generators.factories.TestRows;
import ru.clevertec.task.models.Item;
import ru.clevertec.task.models.Receipt;
import ru.clevertec.task.models.file_items.Column;
import ru.clevertec.task.models.file_items.Row;
import ru.clevertec.task.utils.factories.ItemWriterFactory;

import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static ru.clevertec.task.generators.factories.TestItems.getItemWithoutDiscount;
import static ru.clevertec.task.generators.factories.TestReceipts.*;
import static ru.clevertec.task.generators.factories.TestRows.*;

@SpringBootTest
class DiscountReceiptWriterTest {
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
    private DiscountReceiptWriter receiptWriter;

    @BeforeEach
    void setUp() {
        receiptWriter = new DiscountReceiptWriter(getReceiptWithDiscountCard(), printWriter, factory);
    }

    @Test
    void checkWriteFooterShouldWriteRowsWithSubtotalDiscountTotalPrice() {
        receiptWriter.writeFooter();
        verify(printWriter, times(3)).println(rowCaptor.capture());
        List<Row> expected = List.of(getSubtotalPriceRow(), getCardDiscountRow(), getTotalPriceWithDiscountCardRow());
        List<Row> actual = rowCaptor.getAllValues();
        assertEquals(expected, actual);
    }
}