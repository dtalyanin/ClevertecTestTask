package ru.clevertec.task.dao.impl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.clevertec.task.exceptions.OrderItemNotFoundException;
import ru.clevertec.task.models.DiscountCard;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class DiscountCardDAOImplTest {
    @Mock
    private JdbcTemplate template;
    @InjectMocks
    private DiscountCardDAOImpl discountCardDAO;

    @Test
    void getDiscountCardById() {
        DiscountCard card = new DiscountCard(1234, 10);
        when(template.queryForObject(anyString(), any(BeanPropertyRowMapper.class), eq(1234))).thenReturn(card);
        when(template.queryForObject(anyString(), any(BeanPropertyRowMapper.class), eq(1235)))
                .thenThrow(new EmptyResultDataAccessException(1));
        assertEquals(card, discountCardDAO.getDiscountCardById(1234));
        OrderItemNotFoundException exception = assertThrows(OrderItemNotFoundException.class,
                () -> discountCardDAO.getDiscountCardById(1235));
        String expectedMessage = "Discount card with this number not found";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
        int expectedWrongValue = 1235;
        int wrongValue = exception.getMissingId();
        assertEquals(expectedWrongValue, wrongValue);
    }
}