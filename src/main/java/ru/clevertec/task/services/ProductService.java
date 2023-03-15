package ru.clevertec.task.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.task.dao.ProductDAO;
import ru.clevertec.task.models.Product;

import java.util.List;

/**
 * Service for performing operations with products from the database
 */
@Service
public class ProductService {
    private final ProductDAO dao;

    @Autowired
    public ProductService(ProductDAO dao) {
        this.dao = dao;
    }

    /**
     * Get all products from the database
     *
     * @return List of products from the database
     */
    public List<Product> getAllProducts() {
        return dao.getAllProducts();
    }

    /**
     * Get product from the database by its ID
     *
     * @param id product ID to search
     * @return product with specified id
     */
    public Product getProductById(int id) {
        return dao.getProductById(id);
    }

    /**
     * Add new product in the database
     *
     * @param product product to add
     * @return string message of the operation result
     */
    public int addNewProduct(Product product) {
        return dao.addNewProduct(product);
    }

    /**
     * Change product fields with specified ID in the database
     *
     * @param id      product ID to update
     * @param product product with new values for update
     * @return string message of the operation result
     */
    public String updateProduct(int id, Product product) {
        int updatedRows = dao.updateProduct(id, product);
        return updatedRows != 0 ? "Updating successful" :
                "Updating error: product with ID " + id + " not found.";
    }

    /**
     * Delete product with specified ID in the database
     *
     * @param id product ID to delete
     * @return string message of the operation result
     */
    public String deleteProductById(int id) {
        int updatedRows = dao.deleteProductById(id);
        return updatedRows != 0 ? "Deleting successful" : "Deleting error: product with ID " + id + " not found.";
    }
}
