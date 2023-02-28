package ru.clevertec.task.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.clevertec.task.dao.ProductDAO;
import ru.clevertec.task.exceptions.OrderItemNotFoundException;
import ru.clevertec.task.models.Product;

@Repository
public class ProductDAOImpl implements ProductDAO {
    private static final String SELECT_PRODUCT_BY_ID = "SELECT * FROM product WHERE id = ?";
    private final JdbcTemplate template;

    @Autowired
    public ProductDAOImpl(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Product getProductById(int id) {
        try {
            return template.queryForObject(SELECT_PRODUCT_BY_ID,  new BeanPropertyRowMapper<>(Product.class), id);
        } catch (EmptyResultDataAccessException e) {
            throw new OrderItemNotFoundException("Product with this ID not found.", id);
        }
    }
}