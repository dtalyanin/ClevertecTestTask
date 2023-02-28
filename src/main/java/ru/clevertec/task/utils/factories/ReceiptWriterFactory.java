package ru.clevertec.task.utils.factories;

import org.springframework.stereotype.Component;
import ru.clevertec.task.models.Receipt;
import ru.clevertec.task.services.receipt_writers.DiscountReceiptWriter;
import ru.clevertec.task.services.receipt_writers.ReceiptWriter;
import ru.clevertec.task.services.receipt_writers.SimpleReceiptWriter;

import java.io.PrintWriter;

@Component
public class ReceiptWriterFactory {

    public ReceiptWriter getReceiptWriter(Receipt receipt, PrintWriter writer) {
        ReceiptWriter receiptWriter;
        if (receipt.discountCard()) {
            receiptWriter = new DiscountReceiptWriter(receipt, writer, new ItemWriterFactory());
        } else {
            receiptWriter = new SimpleReceiptWriter(receipt, writer, new ItemWriterFactory());
        }
        return receiptWriter;
    }
}
