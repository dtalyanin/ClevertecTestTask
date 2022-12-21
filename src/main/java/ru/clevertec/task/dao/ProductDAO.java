package ru.clevertec.task.dao;

import ru.clevertec.task.models.Product;

public interface ProductDAO {
    Product getProductById(int id);
}
