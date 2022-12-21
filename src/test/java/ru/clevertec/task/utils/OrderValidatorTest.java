package ru.clevertec.task.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.clevertec.task.exceptions.OrderParamException;
import ru.clevertec.task.models.OrderDTO;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderValidatorTest {

    @Autowired
    private OrderValidator validator;

    @Test
    void validate() {
        OrderDTO dto = new OrderDTO();

        //throw if empty ordered products
        OrderParamException exception = assertThrows(OrderParamException.class, () -> validator.validate(dto));
        String expectedMessage = "Request does not contain any products";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        //correct dto
        Map<Integer, Integer> orderedProducts = IntStream.range(1, 5)
                .boxed()
                .collect(Collectors.toMap(Function.identity(), Function.identity()));
        dto.setOrderedProducts(orderedProducts);
        assertDoesNotThrow(() -> validator.validate(dto));

        //dto with wrong quantity or id
        dto.addProduct(0, 10);
        dto.addProduct(5, 0);
        exception = assertThrows(OrderParamException.class, () -> validator.validate(dto));
        expectedMessage = "Invalid params";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
        String expectedWrongValue = "item0=10, item5=0";
        String wrongValue = exception.getWrongValue();
        assertEquals(expectedWrongValue, wrongValue);
    }
}