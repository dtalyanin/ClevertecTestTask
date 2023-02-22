package ru.clevertec.task.utils.factories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.clevertec.task.generators.factories.TestItems;
import ru.clevertec.task.generators.factories.TestRows;
import ru.clevertec.task.models.Item;
import ru.clevertec.task.models.file_items.Row;
import ru.clevertec.task.services.item_writers.ItemWriter;
import ru.clevertec.task.services.item_writers.ItemWriterDecorator;
import ru.clevertec.task.services.item_writers.ItemWriterImpl;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemWriterFactoryTest {

    @Autowired
    private ItemWriterFactory factory;
    private Row simpleRow;

    @BeforeEach
    void setUp() {
        simpleRow = TestRows.getSimpleRow();
    }

    @Test
    void checkGetItemWriterShouldBeItemWriterImpl() {
        Item item = TestItems.getItemWithoutDiscount();
        Class<ItemWriterImpl> expectedClass = ItemWriterImpl.class;
        Class<? extends ItemWriter> actualClass = factory.getItemWriter(item, simpleRow).getClass();
        assertEquals(expectedClass, actualClass);
    }

    @Test
    void checkGetItemWriterShouldBeItemWriterDecorator() {
        Item item = TestItems.getPromotionalItemWithDiscount();
        Class<ItemWriterDecorator> expectedClass = ItemWriterDecorator.class;
        Class<? extends ItemWriter> actualClass = factory.getItemWriter(item, simpleRow).getClass();
        assertEquals(expectedClass, actualClass);
    }
}