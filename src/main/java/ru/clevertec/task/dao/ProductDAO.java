package ru.clevertec.task.dao;

import ru.clevertec.task.models.Product;

import java.util.List;

public interface ProductDAO {
    List<Product> getAllProducts();
    Product getProductById(int id);
    int addNewProduct(Product product);
    int updateProduct(int id, Product product);
    int deleteProductById(int id);
}
