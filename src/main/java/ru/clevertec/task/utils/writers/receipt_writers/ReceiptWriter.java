package ru.clevertec.task.utils.writers.receipt_writers;

public interface ReceiptWriter {
    void writeHeader();
    void writeLine();
    void writeItems();
    void writeFooter();
}
