package ru.clevertec.task.generators.builders;

import lombok.*;
import ru.clevertec.task.models.OrderDTO;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(staticName = "anOrder")
public class TestOrderDTOBuilder {
    private Map<Integer, Integer> orderedProducts = new HashMap<>();
    private Integer discount;

    public TestOrderDTOBuilder OrderedProducts(Map<Integer, Integer> orderedProducts) {
        this.orderedProducts = orderedProducts;
        return this;
    }

    public TestOrderDTOBuilder OrderedProduct(Integer productId, Integer quantity) {
        this.orderedProducts.put(productId, quantity);
        return this;
    }

    public TestOrderDTOBuilder discount(Integer discount) {
        this.discount = discount;
        return this;
    }

    public OrderDTO build() {
        return new OrderDTO(orderedProducts, discount);
    }
}
