package ru.clevertec.task.utils.factories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.clevertec.task.models.Receipt;
import ru.clevertec.task.utils.writers.receipt_writers.DiscountReceiptWriter;
import ru.clevertec.task.utils.writers.receipt_writers.ReceiptWriter;
import ru.clevertec.task.utils.writers.receipt_writers.SimpleReceiptWriter;

import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ReceiptWriterFactoryTest {

    @Autowired
    private ReceiptWriterFactory factory;
    @Test
    void getReceiptWriter() {
        PrintWriter writer = null;
        Receipt receipt = Receipt.builder().build();
        Class expectedClass = SimpleReceiptWriter.class;
        ReceiptWriter actualReceiptWriter = factory.getReceiptWriter(receipt, writer);
        assertEquals(expectedClass, actualReceiptWriter.getClass());

        receipt = Receipt.builder().discount(10).build();
        expectedClass = DiscountReceiptWriter.class;
        actualReceiptWriter = factory.getReceiptWriter(receipt, writer);
        assertEquals(expectedClass, actualReceiptWriter.getClass());
    }
}