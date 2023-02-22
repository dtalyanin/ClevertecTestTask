package ru.clevertec.task.utils.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.clevertec.task.exceptions.OrderParamException;
import ru.clevertec.task.generators.factories.TestOrdersDTO;
import ru.clevertec.task.models.OrderDTO;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderDTOMapperTest {
    @Autowired
    private OrderDTOMapper mapper;
    private Map<String, String> params;

    @BeforeEach
    public void setUp() {
        params = new HashMap<>();
    }

    @Test
    void checkConvertMapToOrderDTOShouldCreateDTOWithOnlyItems() {
        params.put("item1", "10");
        OrderDTO expected = TestOrdersDTO.orderWithoutDiscount();
        OrderDTO actual = mapper.convertMapToOrderDTO(params);
        assertEquals(expected, actual);
    }

    @Test
    void checkConvertMapToOrderDTOShouldCreateDTOWithItemsAndDiscount() {
        params.put("item1", "10");
        params.put("card", "1234");
        OrderDTO expected = TestOrdersDTO.orderWithDiscount();
        OrderDTO actual = mapper.convertMapToOrderDTO(params);
        assertEquals(expected, actual);
    }

    @Test
    void checkConvertMapToOrderDTOShouldCreateDTOWithoutItemsAndDiscount() {
        OrderDTO expected = TestOrdersDTO.emptyOrder();
        OrderDTO actual = mapper.convertMapToOrderDTO(params);
        assertEquals(expected, actual);
    }

    @Test
    void checkConvertMapToOrderDTOShouldCreateDTOWithoutItemsButWithDiscount() {
        params.put("card", "1234");
        OrderDTO expected = TestOrdersDTO.orderWithOnlyDiscount();
        OrderDTO actual = mapper.convertMapToOrderDTO(params);
        assertEquals(expected, actual);
    }

    @ParameterizedTest(name = "{0} should not convert to Item")
    @ValueSource(strings = { "item1a", "item","item 1", "item-1" })
    void checkConvertMapToOrderDTOShouldCreateDTOWithoutItemsBecauseWrongKey(String key) {
        params.put(key, "1");
        OrderDTO expected = TestOrdersDTO.emptyOrder();
        OrderDTO actual = mapper.convertMapToOrderDTO(params);
        assertEquals(expected, actual);
    }

    @Test
    void checkConvertMapToOrderDTOShouldCreateDTOWithoutDiscountBecauseWrongItemKey() {
        params.put("card5", "1234");
        OrderDTO expected = TestOrdersDTO.emptyOrder();
        OrderDTO actual = mapper.convertMapToOrderDTO(params);
        assertEquals(expected, actual);
    }

    @Test
    void checkConvertMapToOrderDTOShouldThrowExceptionWithInvalidNumberInItemQuantity() {
        params.put("item1", "1abc");
        OrderParamException exception = assertThrows(OrderParamException.class, () -> mapper.convertMapToOrderDTO(params));
        String expectedMessage = "Invalid value for number";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
        String expectedWrongValue = "1abc";
        String actualWrongValue = exception.getWrongValue();
        assertEquals(expectedWrongValue, actualWrongValue);
    }

    @Test
    void checkConvertMapToOrderDTOShouldThrowExceptionWithInvalidNumberInDiscount() {
        params.put("card", "1abc");
        OrderParamException exception = assertThrows(OrderParamException.class, () -> mapper.convertMapToOrderDTO(params));
        String expectedMessage = "Invalid value for number";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
        String expectedWrongValue = "1abc";
        String actualWrongValue = exception.getWrongValue();
        assertEquals(expectedWrongValue, actualWrongValue);
    }
}