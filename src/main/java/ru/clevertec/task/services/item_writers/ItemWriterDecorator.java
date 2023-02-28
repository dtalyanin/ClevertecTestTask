package ru.clevertec.task.services.item_writers;

import ru.clevertec.task.models.Item;
import ru.clevertec.task.models.file_items.Row;
import ru.clevertec.task.utils.PriceHelper;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class ItemWriterDecorator implements ItemWriter {
    private final ItemWriter itemWriter;

    public ItemWriterDecorator(ItemWriter itemWriter) {
        this.itemWriter = itemWriter;
    }

    @Override
    public void writeItem(Item item, PrintWriter writer) {
        itemWriter.writeItem(item, writer);
        List<String> discountValues = getDiscountValues(item);
        Row discountRow = getRow(discountValues);
        writer.println(discountRow);
    }

    @Override
    public Row getRow(List<String> columnValues) {
        return itemWriter.getRow(columnValues);
    }

    private List<String> getDiscountValues(Item item) {
        String empty = "";
        String description = "Promotional discount";
        String percent= item.discountPercent() + "%";
        String discount = "-" + PriceHelper.getPriceRepresentation(item.discount());
        return Arrays.asList(empty, description, percent, discount);
    }
}
