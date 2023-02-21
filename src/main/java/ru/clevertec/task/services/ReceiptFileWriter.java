package ru.clevertec.task.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.task.exceptions.FileWritingException;
import ru.clevertec.task.models.Receipt;
import ru.clevertec.task.utils.factories.ReceiptWriterFactory;
import ru.clevertec.task.services.receipt_writers.ReceiptWriter;

import java.io.*;

@Service
public class ReceiptFileWriter {

    private static final String RESOURCE_PATH = "./src/main/resources/receipts/";
    private final ReceiptWriterFactory factory;

    @Autowired
    public ReceiptFileWriter(ReceiptWriterFactory factory) {
        this.factory = factory;
    }

    public byte[] writeReceipt(Receipt receipt, String fileName) {
        File file = new File(RESOURCE_PATH + fileName);
        try {
            file.createNewFile();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            PrintWriter writer = new PrintWriter(byteArrayOutputStream);

            ReceiptWriter receiptWriter = factory.getReceiptWriter(receipt, writer);
            receiptWriter.writeHeader();
            receiptWriter.writeLine();
            receiptWriter.writeItems();
            receiptWriter.writeLine();
            receiptWriter.writeFooter();

            writer.close();
            FileOutputStream fileOutput = new FileOutputStream(file);
            byteArrayOutputStream.writeTo(fileOutput);
            byte[] data = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            fileOutput.close();
            return data;
        } catch (IOException e) {
            throw new FileWritingException(e.getMessage());
        }
    }
}
