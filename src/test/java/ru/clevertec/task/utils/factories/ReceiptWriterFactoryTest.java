package ru.clevertec.task.utils.factories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.clevertec.task.models.Receipt;
import ru.clevertec.task.services.receipt_writers.DiscountReceiptWriter;
import ru.clevertec.task.services.receipt_writers.ReceiptWriter;
import ru.clevertec.task.services.receipt_writers.SimpleReceiptWriter;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ReceiptWriterFactoryTest {

    @Autowired
    private ReceiptWriterFactory factory;
    private PrintWriter writer;

    @BeforeEach
    void setUp() {
        writer = new PrintWriter(new ByteArrayOutputStream());
    }

    @Test
    void checkGetReceiptWriterShouldBeSimpleWriter() {
        Receipt receipt = Receipt.builder().build();
        Class<SimpleReceiptWriter> expectedClass = SimpleReceiptWriter.class;
        Class<? extends ReceiptWriter> actualClass = factory.getReceiptWriter(receipt, writer).getClass();
        assertEquals(expectedClass, actualClass);
    }

    @Test
    void checkGetReceiptWriterShouldBeDiscountWriter() {
        Receipt receipt = Receipt.builder().discount(10).build();
        Class<DiscountReceiptWriter> expectedClass = DiscountReceiptWriter.class;
        Class<? extends ReceiptWriter> actualClass = factory.getReceiptWriter(receipt, writer).getClass();
        assertEquals(expectedClass, actualClass);
    }
}