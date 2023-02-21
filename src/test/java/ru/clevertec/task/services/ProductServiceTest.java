package ru.clevertec.task.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.clevertec.task.dao.impl.ProductDAOImpl;
import ru.clevertec.task.models.Product;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static ru.clevertec.task.generators.factories.TestProducts.getTestProduct;

@SpringBootTest
class ProductServiceTest {

    @Mock
    private ProductDAOImpl dao;
    @InjectMocks
    private ProductService service;
    private Product product;

    @BeforeEach
    void setUp() {
        product = getTestProduct();
    }

    @Test
    void checkGetAllProductsShouldReturnList() {
        List<Product> expected = List.of(product, product);
        when(dao.getAllProducts()).thenReturn(expected);
        List<Product> actual = service.getAllProducts();
        assertEquals(expected, actual);
        verify(dao, times(1)).getAllProducts();
    }

    @Test
    void checkGetProductByIdShouldReturnProduct() {
        when(dao.getProductById(1)).thenReturn(product);
        Product actual = service.getProductById(1);
        assertEquals(product, actual);
    }

    @Test
    void checkAddNewProduct() {
        when(dao.addNewProduct(product)).thenReturn(1);
        String expected = "Adding successful";
        String actual = service.addNewProduct(product);
        assertEquals(expected, actual);
    }

    @Test
    void checkUpdateProduct() {
        when(dao.updateProduct(1, product)).thenReturn(1);
        String expected = "Updating successful";
        String actual = service.updateProduct(1, product);
        assertEquals(expected, actual);
    }

    @Test
    void checkUpdateProductShouldThrowExceptionNoProductId() {
        when(dao.updateProduct(1, product)).thenReturn(0);
        String expected = "Updating error: product with ID 1 not found.";
        String actual = service.updateProduct(1, product);
        assertEquals(expected, actual);
    }

    @Test
    void checkDeleteProductById() {
        when(dao.deleteProductById(1)).thenReturn(1);
        String expected = "Deleting successful";
        String actual = service.deleteProductById(1);
        assertEquals(expected, actual);
    }

    @Test
    void checkDeleteProductByIdShouldThrowExceptionNoProductId() {
        when(dao.updateProduct(1, product)).thenReturn(0);
        String expected = "Deleting error: product with ID 1 not found.";
        String actual = service.deleteProductById(1);
        assertEquals(expected, actual);
    }
}