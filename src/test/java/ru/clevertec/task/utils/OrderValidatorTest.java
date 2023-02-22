package ru.clevertec.task.utils;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.clevertec.task.exceptions.OrderParamException;
import ru.clevertec.task.generators.factories.TestOrdersDTO;
import ru.clevertec.task.models.OrderDTO;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderValidatorTest {

    @Autowired
    private OrderValidator validator;

    @Test
    void checkValidateShouldWorkCorrectly() {
        OrderDTO dto = Mockito.spy(TestOrdersDTO.orderWithDiscount());
        assertDoesNotThrow(() -> validator.validate(dto));
        Mockito.verify(dto, Mockito.times(2)).getOrderedProducts();
    }

    @Test
    void checkValidateShouldThrowExceptionNoProducts() {
        OrderDTO dto = TestOrdersDTO.emptyOrder();
        OrderParamException exception = assertThrows(OrderParamException.class, () -> validator.validate(dto));
        String expectedMessage = "Request does not contain any products";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void checkValidateShouldThrowExceptionIncorrectQuantity() {
        OrderDTO dto = TestOrdersDTO.orderWithIncorrectQuantity();
        OrderParamException exception = assertThrows(OrderParamException.class, () -> validator.validate(dto));

        String expectedMessage = "Invalid params";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        String expectedWrongValue = "item1=0, item2=0";
        String actualWrongValue = exception.getWrongValue();
        assertEquals(expectedWrongValue, actualWrongValue);
    }

    @Test
    void checkValidateShouldThrowExceptionIncorrectId() {
        OrderDTO dto = TestOrdersDTO.orderWithIncorrectId();
        OrderParamException exception = assertThrows(OrderParamException.class, () -> validator.validate(dto));

        String expectedMessage = "Invalid params";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        String expectedWrongValue = "item0=1";
        String actualWrongValue = exception.getWrongValue();
        assertEquals(expectedWrongValue, actualWrongValue);
    }
}