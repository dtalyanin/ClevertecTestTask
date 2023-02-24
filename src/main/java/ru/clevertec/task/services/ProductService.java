package ru.clevertec.task.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.task.dao.ProductDAO;
import ru.clevertec.task.models.Product;

import java.util.List;

@Service
public class ProductService {
    private final ProductDAO dao;


    @Autowired
    public ProductService(ProductDAO dao) {
        this.dao = dao;
    }

    public List<Product> getAllProducts() {
        return dao.getAllProducts();
    }

    public Product getProductById(int id) {
        return dao.getProductById(id);
    }

    public String addNewProduct(Product product) {
        dao.addNewProduct(product);
        return "Adding successful";
    }

    public String updateProduct(int id, Product product) {
        int updatedRows = dao.updateProduct(id, product);
        return updatedRows != 0 ? "Updating successful" :
                "Updating error: product with ID " + id + " not found.";
    }

    public String deleteProductById(int id) {
        int updatedRows = dao.deleteProductById(id);
        return updatedRows != 0 ? "Deleting successful" : "Deleting error: product with ID " + id + " not found.";
    }
}
