package ru.clevertec.task.dao.impl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.clevertec.task.exceptions.OrderItemNotFoundException;
import ru.clevertec.task.models.Product;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductDAOImplTest {
    @Mock
    private JdbcTemplate template;
    @InjectMocks
    private ProductDAOImpl productDAO;

    @Test
    void getProductById() {
        Product product = new Product(1, "Test product", "1 l.", 150, false);
        when(template.queryForObject(anyString(), any(BeanPropertyRowMapper.class), eq(1))).thenReturn(product);
        when(template.queryForObject(anyString(), any(BeanPropertyRowMapper.class), eq(2)))
                .thenThrow(new EmptyResultDataAccessException(1));
        assertEquals(product, productDAO.getProductById(1));
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