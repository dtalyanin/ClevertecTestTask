package ru.clevertec.task.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.clevertec.task.dao.DiscountCardDAO;
import ru.clevertec.task.exceptions.OrderItemNotFoundException;
import ru.clevertec.task.models.DiscountCard;

@Repository
public class DiscountCardDAOImpl implements DiscountCardDAO {
    private static final String SELECT_PRODUCT_BY_ID = "SELECT * FROM discount_card WHERE card_number = ?";
    private final JdbcTemplate template;

    @Autowired
    public DiscountCardDAOImpl(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public DiscountCard getDiscountCardById(int cardNumber) {
        try {
            return template.queryForObject(SELECT_PRODUCT_BY_ID,  new BeanPropertyRowMapper<>(DiscountCard.class), cardNumber);
        } catch (EmptyResultDataAccessException e) {
            throw new OrderItemNotFoundException("Discount card with this number not found", cardNumber);
        }
    }
}
