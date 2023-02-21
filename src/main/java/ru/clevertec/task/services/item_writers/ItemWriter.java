package ru.clevertec.task.services.item_writers;

import ru.clevertec.task.models.Item;
import ru.clevertec.task.models.file_items.Row;

import java.io.PrintWriter;
import java.util.List;

public interface ItemWriter {
    void writeItem(Item item, PrintWriter writer);
    Row getRow(List<String> columnValues);
}
