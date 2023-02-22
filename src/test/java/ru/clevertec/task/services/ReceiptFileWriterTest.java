package ru.clevertec.task.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.clevertec.task.exceptions.FileWritingException;
import ru.clevertec.task.generators.factories.TestReceipts;
import ru.clevertec.task.models.Receipt;
import ru.clevertec.task.services.receipt_writers.ReceiptWriter;
import ru.clevertec.task.utils.factories.ReceiptWriterFactory;

import java.io.File;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ReceiptFileWriterTest {
    @Mock
    private ReceiptWriter receiptWriter;
    @Mock
    private ReceiptWriterFactory factory;
    @InjectMocks
    private ReceiptFileWriter receiptFileWriter;

    @Test
    void checkWriteReceiptShouldCreateFile() {
        when(factory.getReceiptWriter(any(Receipt.class), any(PrintWriter.class))).thenReturn(receiptWriter);
        receiptFileWriter.writeReceipt(TestReceipts.getSimpleReceipt(), "test.txt");
        verify(factory, times(1)).getReceiptWriter(any(Receipt.class), any(PrintWriter.class));
        verify(receiptWriter, times(1)).writeHeader();
        verify(receiptWriter, times(2)).writeLine();
        verify(receiptWriter, times(1)).writeItems();
        verify(receiptWriter, times(1)).writeFooter();
        File file = new File("./src/main/resources/receipts/test.txt");
        assertTrue(file.exists());
        file.delete();
    }

    @Test
    void checkWriteReceiptShouldThrowFileWritingExceptionWithInvalidPath() {
        String invalidFileName = "\u0000";
        FileWritingException exception = assertThrows(FileWritingException.class,
                () -> receiptFileWriter.writeReceipt(Receipt.builder().build(), invalidFileName));
        String expectedMessage = "Invalid file path";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }
}