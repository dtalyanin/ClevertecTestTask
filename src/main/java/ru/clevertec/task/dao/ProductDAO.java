package ru.clevertec.task.dao;

import ru.clevertec.task.models.Product;

import java.util.List;

/**
 * Interface for performing operations with products from the database
 */
public interface ProductDAO {
    /**
     * Get all products from the database
     *
     * @return list of products from the database
     */
    List<Product> getAllProducts();

    /**
     * Get product from the database by its ID
     *
     * @param id product ID to search
     * @return product with specified id
     */
    Product getProductById(int id);

    /**
     * Add new product in the database
     *
     * @param product product to add
     * @return generated ID for new product
     */
    int addNewProduct(Product product);

    /**
     * change product fields with specified ID in the database
     *
     * @param id      product ID to update
     * @param product product with new values for update
     * @return affected rows number
     */
    int updateProduct(int id, Product product);

    /**
     * delete product with specified ID in the database
     *
     * @param id product ID to delete
     * @return affected rows number
     */
    int deleteProductById(int id);
}
