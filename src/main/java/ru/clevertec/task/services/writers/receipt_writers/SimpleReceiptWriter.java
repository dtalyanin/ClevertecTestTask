package ru.clevertec.task.services.writers.receipt_writers;


import org.springframework.beans.factory.annotation.Autowired;
import ru.clevertec.task.models.Receipt;
import ru.clevertec.task.models.Item;
import ru.clevertec.task.services.writers.file_items.AlignFormatter;
import ru.clevertec.task.services.writers.file_items.Column;
import ru.clevertec.task.services.writers.file_items.Row;
import ru.clevertec.task.services.writers.item_writers.ItemWriter;
import ru.clevertec.task.utils.factories.ItemWriterFactory;
import ru.clevertec.task.utils.DateHelper;
import ru.clevertec.task.utils.PriceHelper;

import java.io.*;
import java.util.Arrays;

public class SimpleReceiptWriter implements ReceiptWriter {
    protected final Receipt receipt;
    protected final PrintWriter writer;
    protected final Row mainRow;
    protected final int maxRowLength;
    protected final String lineDelimiter;
    protected final ItemWriterFactory factory;

    public SimpleReceiptWriter(Receipt receipt, PrintWriter writer, @Autowired ItemWriterFactory factory) {
        this.receipt = receipt;
        this.writer = writer;
        this.mainRow = new Row(Arrays.asList(
                new Column("QTY", 3, AlignFormatter.CENTER),
                new Column("DESCRIPTION", 28, AlignFormatter.LEFT),
                new Column("PRICE", 7, AlignFormatter.RIGHT),
                new Column("TOTAL", 9, AlignFormatter.RIGHT)),
                " ");
        this.maxRowLength = mainRow.toString().length();
        this.lineDelimiter = "_".repeat(maxRowLength);
        this.factory = factory;
    }

    @Override
    public void writeHeader() {
        Column header = new Column("CASH RECEIPT", maxRowLength, AlignFormatter.CENTER);
        writer.println(header);
        Column date = new Column(DateHelper.getDate(receipt.time()), maxRowLength, AlignFormatter.RIGHT);
        writer.println(date);
        Column time = new Column(DateHelper.getTime(receipt.time()), maxRowLength, AlignFormatter.RIGHT);
        writer.println(time);
    }

    @Override
    public void writeLine() {
        writer.println(lineDelimiter);
    }

    public void writeItems() {
        for (Item item : receipt.items()) {
            ItemWriter itemWriter = factory.getItemWriter(item, mainRow);
            itemWriter.writeItem(item, writer);
        }
    }

    @Override
    public void writeFooter() {
        int totalPrice = receipt.totalPrice();
        String strTotalCost = PriceHelper.getPriceRepresentation(totalPrice);
        writeTwoColumnsInRowWithEdgeAlign("TOTAL", strTotalCost);
    }

    protected void writeTwoColumnsInRowWithEdgeAlign(String leftValue, String rightValue) {
        writeTwoColumnsInRow(AlignFormatter.LEFT, leftValue, AlignFormatter.RIGHT, rightValue);
    }

    protected void writeTwoColumnsInRow(AlignFormatter leftColumnAlign, String leftValue,
                                      AlignFormatter rightColumnAlign, String rightValue) {
        String columnDelimiter = mainRow.delimiter();
        int leftLength = (maxRowLength - columnDelimiter.length()) / 2;
        int rightLength = maxRowLength - leftLength - columnDelimiter.length();
        Row row = new Row(Arrays.asList(
                new Column(leftValue, leftLength, leftColumnAlign),
                new Column(rightValue, rightLength, rightColumnAlign)),
                columnDelimiter);
        writer.println(row);
    }
}
