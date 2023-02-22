package ru.clevertec.task.services;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.clevertec.task.dao.DiscountCardDAO;
import ru.clevertec.task.dao.ProductDAO;
import ru.clevertec.task.generators.factories.TestOrdersDTO;
import ru.clevertec.task.models.DiscountCard;
import ru.clevertec.task.models.OrderDTO;
import ru.clevertec.task.models.Product;
import ru.clevertec.task.models.Receipt;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static ru.clevertec.task.generators.factories.TestReceipts.getReceiptWithDiscountCard;
import static ru.clevertec.task.generators.factories.TestReceipts.getSimpleReceipt;

@SpringBootTest
class ReceiptServiceTest {
    @MockBean
    private ProductDAO productDAO;
    @MockBean
    private DiscountCardDAO discountCardDAO;
    @Autowired
    private ReceiptService service;

    @Test
    void checkGetReceiptFromDTOShouldCreateWithoutDiscountCard() {
        Clock clock = Clock.fixed(Instant.parse("2022-02-22T06:00:00Z"), ZoneId.of("UTC"));
        try (MockedStatic<Clock> mockedStatic = mockStatic(Clock.class)) {
            mockedStatic.when(Clock::systemDefaultZone).thenReturn(clock);
            when(productDAO.getProductById(anyInt())).thenReturn(new Product(1, "Test", "1 l.", 100, false));
            OrderDTO dto = TestOrdersDTO.orderWithoutDiscount();
            Receipt expected = getSimpleReceipt();
            Receipt actual = service.getReceiptFromDTO(dto);
            verify(discountCardDAO, never()).getDiscountCardById(anyInt());
            verify(productDAO, times(1)).getProductById(anyInt());
            assertEquals(expected, actual);
        }
    }

    @Test
    void checkGetReceiptFromDTOShouldCreateWithDiscountCard() {
        Clock clock = Clock.fixed(Instant.parse("2022-02-22T06:00:00Z"), ZoneId.of("UTC"));
        try (MockedStatic<Clock> mockedStatic = mockStatic(Clock.class)) {
            mockedStatic.when(Clock::systemDefaultZone).thenReturn(clock);
            when(productDAO.getProductById(anyInt())).thenReturn(new Product(1, "Test", "1 l.", 100, false));
            when(discountCardDAO.getDiscountCardById(1234)).thenReturn(new DiscountCard(1234, 10));
            OrderDTO dto = TestOrdersDTO.orderWithDiscount();
            Receipt expected = getReceiptWithDiscountCard();
            Receipt actual = service.getReceiptFromDTO(dto);
            verify(discountCardDAO, times(1)).getDiscountCardById(anyInt());
            verify(productDAO, times(1)).getProductById(anyInt());
            assertEquals(expected, actual);
        }
    }
}