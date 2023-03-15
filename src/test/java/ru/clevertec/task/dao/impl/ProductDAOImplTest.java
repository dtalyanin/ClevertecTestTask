package ru.clevertec.task.dao.impl;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.clevertec.task.exceptions.OrderItemNotFoundException;
import ru.clevertec.task.models.Product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductDAOImplTest {
    @Captor
    private ArgumentCaptor<Integer> intCaptor;
    @Mock
    private JdbcTemplate template;
    @InjectMocks
    private ProductDAOImpl productDAO;

    @Test
    void checkGetProductByIdShouldReturnProduct() {
        Product expected = new Product(1, "Test product", "1 l.", 150, false);
        int expectedId = 1;
        when(template.queryForObject(anyString(), any(BeanPropertyRowMapper.class), intCaptor.capture()))
                .thenReturn(expected);
        Product actual = productDAO.getProductById(expectedId);
        assertEquals(expected, actual);
        int actualId = intCaptor.getValue();
        assertEquals(expectedId, actualId);
    }

    @Test
    void checkGetProductByIdShouldThrowExceptionNoProductWithId() {
        when(template.queryForObject(anyString(), any(BeanPropertyRowMapper.class), anyInt()))
                .thenThrow(new EmptyResultDataAccessException(1));
        OrderItemNotFoundException exception = assertThrows(OrderItemNotFoundException.class,
                () -> productDAO.getProductById(2));
        String expectedMessage = "Product with this ID not found.";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
        int expectedWrongValue = 2;
        int wrongValue = exception.getMissingId();
        assertEquals(expectedWrongValue, wrongValue);
    }
}