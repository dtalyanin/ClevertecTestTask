package ru.clevertec.task.utils.writers.item_writers;

import ru.clevertec.task.exceptions.FileWritingException;
import ru.clevertec.task.models.Item;
import ru.clevertec.task.utils.writers.file_items.AlignFormatter;
import ru.clevertec.task.utils.writers.file_items.Column;
import ru.clevertec.task.utils.writers.file_items.Row;
import ru.clevertec.task.utils.PriceHelper;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemWriterImpl implements ItemWriter {
    private final Row mainRow;

    public ItemWriterImpl(Row mainRow) {
        this.mainRow = mainRow;
    }

    @Override
    public void writeItem(Item item, PrintWriter writer) {
        List<String> itemValues = getItemValues(item);
        if (checkIsAllFitsInOneLine(itemValues)) {
            writer.println(getRow(itemValues));
        } else {
            writeSplitItem(itemValues, writer);
        }
    }
    
    private List<String> getItemValues(Item item) {
        String quantity = String.valueOf(item.quantity());
        String description = item.name() + ", " + item.measure();
        String price = PriceHelper.getPriceRepresentation(item.price());
        String total = PriceHelper.getPriceRepresentation(item.totalPrice());
        return Arrays.asList(quantity, description, price, total);
    }

    @Override
    public Row getRow(List<String> columnValues) {
        List<Column> mainColumns = mainRow.columns();
        List<Column> itemColumns = new ArrayList<>();
        for (int i = 0; i < columnValues.size(); i++) {
            String value = columnValues.get(i);
            int maxLength = mainColumns.get(i).maxLength();
            AlignFormatter formatter = mainColumns.get(i).alignFormatter();
            itemColumns.add(new Column(value, maxLength, formatter));
        }
        return new Row(itemColumns, mainRow.delimiter());
    }

    private boolean checkIsAllFitsInOneLine(List<String> itemValues) {
        List<Column> mainColumns = mainRow.columns();
        if (itemValues.size() != mainColumns.size()) {
            throw new FileWritingException("Expected columns amount: " + mainColumns.size() +
                    ", actual: " + itemValues.size());
        }
        boolean isAllFitOneLine = true;
        for (int i = 0; i < itemValues.size(); i++) {
            if (itemValues.get(i).length() > mainColumns.get(i).maxLength()) {
                isAllFitOneLine = false;
                break;
            }
        }
        return isAllFitOneLine;
    }

    private void writeSplitItem(List<String> itemValues, PrintWriter writer) {
        List<List<String>> splitItemValues = new ArrayList<>();
        for (int i = 0; i < itemValues.size(); i++) {
            List<String> splitValue = splitValue(itemValues.get(i), mainRow.columns().get(i).maxLength());
            splitItemValues.add(splitValue);
        }
        int maxRowsInSplitValues = splitItemValues.stream().mapToInt(List::size).max().getAsInt();
        String emptyColumnValue = "";
        for (int i = 0; i < maxRowsInSplitValues; i++) {
            List<String> columnsValues = new ArrayList<>();
            for (List<String> splitValue: splitItemValues) {
                if (splitValue.size() > i) {
                    columnsValues.add(splitValue.get(i));
                } else {
                    columnsValues.add(emptyColumnValue);
                }
            }
            writer.println(getRow(columnsValues));
        }
    }

    private List<String> splitValue(String value, int maxLength) {
        List<String> result = new ArrayList<>();
        int length = value.length();
        for (int i = 0; i < length; i += maxLength) {
            result.add(value.substring(i, Math.min(length, i + maxLength)));
        }
        return result;
    }
}
