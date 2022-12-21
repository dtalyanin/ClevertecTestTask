package ru.clevertec.task.services.writers.receipt_writers;

public interface ReceiptWriter {
    void writeHeader();
    void writeLine();
    void writeItems();
    void writeFooter();
}
