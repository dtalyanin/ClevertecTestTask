package ru.clevertec.task.models;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class OrderDTO {
    private Map<Integer, Integer> orderedProducts;
    private Integer discount;

    public OrderDTO() {
        this.orderedProducts = new HashMap<>();
    }

    public void addProduct(Integer product, Integer amount) {
        orderedProducts.put(product, amount);
    }

}
