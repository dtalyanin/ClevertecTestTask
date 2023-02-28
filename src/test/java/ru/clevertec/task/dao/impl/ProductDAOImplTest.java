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
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.KeyHolder;
import ru.clevertec.task.exceptions.OrderItemNotFoundException;
import ru.clevertec.task.models.Product;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.clevertec.task.generators.factories.TestProducts.getTestProduct;

@SpringBootTest
class ProductDAOImplTest {
    @Captor
    private ArgumentCaptor<Integer> intCaptor;
    @Mock
    private JdbcTemplate template;
    @InjectMocks
    private ProductDAOImpl productDAO;

    @Test
    void checkGetAllProductsShouldReturnList() {
        List<Product> expected = List.of(getTestProduct(), getTestProduct());
        when(template.query(anyString(), any(BeanPropertyRowMapper.class))).thenReturn(expected);
        List<Product> actual = productDAO.getAllProducts();
        assertEquals(expected, actual);
        verify(template, times(1)).query(anyString(), any(BeanPropertyRowMapper.class));
    }

    @Test
    void checkGetProductByIdShouldReturnProduct() {
        Product expected = getTestProduct();
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

    @Test
    void checkAddNewProduct() {
        try {
            productDAO.addNewProduct(getTestProduct());
        } catch (NullPointerException e) {
            //ignore, because can't mock KeyHolder
        }
        verify(template, times(1)).update(any(PreparedStatementCreator.class), any(KeyHolder.class));
    }

    @Test
    void checkUpdateProduct() {
        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> measureCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Integer> priceCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Boolean> promotionalCaptor = ArgumentCaptor.forClass(Boolean.class);
        when(template.update(anyString(), nameCaptor.capture(), measureCaptor.capture(),
                priceCaptor.capture(), promotionalCaptor.capture(), intCaptor.capture())).thenReturn(1);
        Product product = getTestProduct();
        int id = 5;
        int expectedAffectedRows = 1;
        int actualAffectedRows = productDAO.updateProduct(id, product);
        assertEquals(id, intCaptor.getValue());
        assertEquals(product.getName(), nameCaptor.getValue());
        assertEquals(product.getMeasure(), measureCaptor.getValue());
        assertEquals(product.getPrice(), priceCaptor.getValue());
        assertEquals(product.isPromotional(), promotionalCaptor.getValue());
        assertEquals(expectedAffectedRows, actualAffectedRows);
    }

    @Test
    void checkDeleteProductById() {
        when(template.update(anyString(), anyInt())).thenReturn(1);
        int expectedAffectedRows = 1;
        int actualAffectedRows = productDAO.deleteProductById(5);
        assertEquals(expectedAffectedRows, actualAffectedRows);
    }
}