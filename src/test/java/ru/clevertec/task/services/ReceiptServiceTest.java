package ru.clevertec.task.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.clevertec.task.dao.DiscountCardDAO;
import ru.clevertec.task.dao.ProductDAO;
import ru.clevertec.task.models.*;
import ru.clevertec.task.utils.mappers.ItemMapper;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReceiptServiceTest {
    @Mock
    private ProductDAO productDAO;
    @Mock
    private DiscountCardDAO discountCardDAO;

    @Test
    void getReceiptFromDTO() {
        ReceiptService service = new ReceiptService(productDAO, discountCardDAO, new ItemMapper());
        when(productDAO.getProductById(1)).thenReturn(new Product(1, "Product", "1 l.", 100, false));
        when(discountCardDAO.getDiscountCardById(1234)).thenReturn(new DiscountCard(1234, 10));
        OrderDTO dto = new OrderDTO();
        dto.addProduct(1, 10);
        Receipt.ReceiptBuilder builder = Receipt.builder();
        Item item = new Item("Product", "1 l.", 100, 10, null, false, 1000, 0, 1000);
        Receipt actual = service.getReceiptFromDTO(dto);
        builder.item(item);
        builder.dateTime(actual.time());
        Receipt expected = builder.build();
        assertEquals(expected, actual);

        dto.setDiscount(1234);
        actual = service.getReceiptFromDTO(dto);
        builder = Receipt.builder();
        builder.item(item);
        builder.discount(10);
        builder.dateTime(actual.time());
        expected = builder.build();
        assertEquals(expected, actual);
    }
}