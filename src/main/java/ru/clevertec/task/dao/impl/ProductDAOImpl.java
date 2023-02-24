package ru.clevertec.task.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.clevertec.task.dao.ProductDAO;
import ru.clevertec.task.exceptions.OrderItemNotFoundException;
import ru.clevertec.task.models.Product;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ProductDAOImpl implements ProductDAO {
    private static final String SELECT_ALL_PRODUCTS = "SELECT * FROM product";
    private static final String SELECT_PRODUCT_BY_ID = "SELECT * FROM product WHERE id = ?";
    private static final String INSERT_NEW_PRODUCT = "INSERT INTO product (name, measure, price, promotional) " +
            "VALUES(?, ?, ?, ?)";
    private static final String UPDATE_PRODUCT = "UPDATE product SET name = ?, measure = ?, price = ?, " +
            "promotional = ? WHERE id = ?";
    private static final String DELETE_PRODUCT = "DELETE FROM product WHERE id = ?";

    private final JdbcTemplate template;

    @Autowired
    public ProductDAOImpl(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<Product> getAllProducts() {
        return template.query(SELECT_ALL_PRODUCTS, new BeanPropertyRowMapper<>(Product.class));
    }

    @Override
    public Product getProductById(int id) {
        try {
            return template.queryForObject(SELECT_PRODUCT_BY_ID, new BeanPropertyRowMapper<>(Product.class), id);
        } catch (EmptyResultDataAccessException e) {
            throw new OrderItemNotFoundException("Product with this ID not found.", id);
        }
    }

    @Override
    public int addNewProduct(Product product) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_NEW_PRODUCT, new String[] {"id"});
            ps.setString(1, product.getName());
            ps.setString(2, product.getMeasure());
            ps.setInt(3, product.getPrice());
            ps.setBoolean(4, product.isPromotional());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public int updateProduct(int id, Product product) {
        return template.update(UPDATE_PRODUCT, product.getName(), product.getMeasure(), product.getPrice(),
                product.isPromotional(), id);
    }

    @Override
    public int deleteProductById(int id) {
        return template.update(DELETE_PRODUCT, id);
    }
}
