package ru.clevertec.task.utils.factories;

import org.springframework.stereotype.Component;
import ru.clevertec.task.models.Item;
import ru.clevertec.task.services.writers.item_writers.ItemWriter;
import ru.clevertec.task.services.writers.item_writers.ItemWriterDecorator;
import ru.clevertec.task.services.writers.item_writers.ItemWriterImpl;
import ru.clevertec.task.services.writers.file_items.Row;

@Component
public class ItemWriterFactory {
    private ItemWriter itemWriter;
    private ItemWriter itemWriterDecorator;

    public ItemWriter getItemWriter(Item item, Row mainRow) {
        if (itemWriter == null) {
            itemWriter = new ItemWriterImpl(mainRow);
        }
        ItemWriter writerToReturn = itemWriter;
        if (item.promotional()) {
            if (itemWriterDecorator == null) {
                itemWriterDecorator = new ItemWriterDecorator(itemWriter);
            }
            writerToReturn = itemWriterDecorator;
        }
        return writerToReturn;
    }
}
