package ru.clevertec.task.utils.mappers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.clevertec.task.models.Item;
import ru.clevertec.task.models.Product;
import ru.clevertec.task.utils.PriceHelper;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemMapperTest {
    @Autowired
    private ItemMapper mapper;

    @Test
    void getItem() {
        //item without promotional
        Product product = new Product(1, "Test product", "1 l.", 150, false);
        int quantity = 10;
        Item item = mapper.getItem(product, quantity);
        assertEquals(product.getName(), item.name());
        assertEquals(product.getMeasure(), item.measure());
        assertEquals(product.getPrice(), item.price());
        assertEquals(product.isPromotional(), item.promotional());
        int expectedTotalPrice = product.getPrice() * quantity;
        int expectedDiscount = 0;
        int expectedTotalPriceWithDiscount = expectedTotalPrice;
        assertEquals(expectedTotalPrice, item.totalPrice());
        assertEquals(expectedDiscount, item.discount());
        assertEquals(expectedTotalPriceWithDiscount, item.totalPriceWithDiscount());

        //item with promotional
        product.setPromotional(true);
        item = mapper.getItem(product, quantity);
        expectedDiscount = PriceHelper.getPricePercentWithRound(product.getPrice(), 10) * quantity;
        expectedTotalPriceWithDiscount = expectedTotalPrice - expectedDiscount;
        assertEquals(expectedDiscount, item.discount());
        assertEquals(expectedTotalPriceWithDiscount, item.totalPriceWithDiscount());

        //item with promotional, but not enough quantity
        quantity = 2;
        item = mapper.getItem(product, quantity);
        expectedTotalPrice = product.getPrice() * quantity;
        expectedDiscount = 0;
        expectedTotalPriceWithDiscount = expectedTotalPrice;
        assertEquals(expectedDiscount, item.discount());
        assertEquals(expectedTotalPriceWithDiscount, item.totalPriceWithDiscount());
    }
}