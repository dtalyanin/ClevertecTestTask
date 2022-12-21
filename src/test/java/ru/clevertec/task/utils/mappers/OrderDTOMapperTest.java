package ru.clevertec.task.utils.mappers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.clevertec.task.exceptions.OrderParamException;
import ru.clevertec.task.models.OrderDTO;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderDTOMapperTest {
    @Autowired
    private OrderDTOMapper mapper;

    @Test
    void convertMapToOrderDTO() {
        //order with only items
        Map<String, String> params = new HashMap<>();
        params.put("item1", "1");
        OrderDTO actual = mapper.convertMapToOrderDTO(params);
        OrderDTO expected = new OrderDTO();
        expected.addProduct(1, 1);
        assertEquals(expected, actual);

        //order with discount
        params.put("card", "1234");
        actual = mapper.convertMapToOrderDTO(params);
        expected.setDiscount(1234);
        assertEquals(expected, actual);

        //wrong number value
        params.put("item2", "abc");
        OrderParamException exception = assertThrows(OrderParamException.class, () -> mapper.convertMapToOrderDTO(params));
        String expectedMessage = "Invalid value for number";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
        String expectedWrongValue = "abc";
        String actualWrongValue = exception.getWrongValue();
        assertEquals(expectedWrongValue, actualWrongValue);
    }
}