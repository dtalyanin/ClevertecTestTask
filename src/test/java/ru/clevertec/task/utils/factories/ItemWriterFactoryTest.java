package ru.clevertec.task.utils.factories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.clevertec.task.models.Item;
import ru.clevertec.task.utils.writers.file_items.Row;
import ru.clevertec.task.utils.writers.item_writers.ItemWriter;
import ru.clevertec.task.utils.writers.item_writers.ItemWriterDecorator;
import ru.clevertec.task.utils.writers.item_writers.ItemWriterImpl;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemWriterFactoryTest {

    @Autowired
    private ItemWriterFactory factory;

    @Test
    void getItemWriter() {
        Item item = new Item("name", "measure", 100, 10, null, false, 1000, 0, 1000);
        Row row = new Row(Collections.emptyList(), "");
        Class expectedClass = ItemWriterImpl.class;
        ItemWriter actualWriter = factory.getItemWriter(item, row);
        assertEquals(expectedClass, actualWriter.getClass());

        item = new Item("name", "measure", 100, 10, 10, true, 1000, 100, 900);
        expectedClass = ItemWriterDecorator.class;
        actualWriter = factory.getItemWriter(item, row);
        assertEquals(expectedClass, actualWriter.getClass());
    }
}