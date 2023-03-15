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
import ru.clevertec.task.models.DiscountCard;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class DiscountCardDAOImplTest {
    @Captor
    private ArgumentCaptor<Integer> intCaptor;
    @Mock
    private JdbcTemplate template;
    @InjectMocks
    private DiscountCardDAOImpl discountCardDAO;

    @Test
    void checkGetDiscountCardByIdShouldReturnCard() {
        DiscountCard expected = new DiscountCard(1234, 10);
        int expectedId = 1234;
        when(template.queryForObject(anyString(), any(BeanPropertyRowMapper.class), intCaptor.capture()))
                .thenReturn(expected);
        assertEquals(expected, discountCardDAO.getDiscountCardById(1234));
        int actualId = intCaptor.getValue();
        assertEquals(expectedId, actualId);
    }

    @Test
    void checkGetDiscountCardByIdShouldShouldThrowExceptionNoDiscountCard() {
        when(template.queryForObject(anyString(), any(BeanPropertyRowMapper.class), anyInt()))
                .thenThrow(new EmptyResultDataAccessException(1));
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