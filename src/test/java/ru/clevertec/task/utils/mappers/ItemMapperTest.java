package ru.clevertec.task.utils.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.clevertec.task.generators.factories.TestItems;
import ru.clevertec.task.models.Item;
import ru.clevertec.task.models.Product;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemMapperTest {
    @Autowired
    private ItemMapper mapper;
    private Product simpleProduct;

    @BeforeEach
    void setUp() {
        simpleProduct = new Product(1, "Test", "1 l.", 100, false);
    }

    @Test
    void checkGetItemShouldBeWithoutDiscount() {
        int quantity = 10;
        Item expected = TestItems.getItemWithoutDiscount();
        Item actual = mapper.getItem(simpleProduct, quantity);
        assertEquals(expected, actual);
    }

    @Test
    void checkGetItemShouldBeWithDiscount() {
        simpleProduct.setPromotional(true);
        int quantity = 10;
        Item expected = TestItems.getPromotionalItemWithDiscount();
        Item actual = mapper.getItem(simpleProduct, quantity);
        assertEquals(expected, actual);
    }

    @Test
    void checkGetItemShouldBeWithoutDiscountBecauseNotEnoughQuantity() {
        simpleProduct.setPromotional(true);
        int quantity = 2;
        Item expected = TestItems.getItemWithoutDiscountWithSmallQuantity();
        Item actual = mapper.getItem(simpleProduct, quantity);
        assertEquals(expected, actual);
    }
}