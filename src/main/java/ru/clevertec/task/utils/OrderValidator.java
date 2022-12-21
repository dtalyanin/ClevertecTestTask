package ru.clevertec.task.utils;

import org.springframework.stereotype.Component;
import ru.clevertec.task.models.OrderDTO;
import ru.clevertec.task.exceptions.OrderParamException;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class OrderValidator {

    public void validate(OrderDTO dto) {
        if (dto.getOrderedProducts().size() == 0) {
            throw new OrderParamException("Request does not contain any products");
        }
        Map<Integer, Integer> wrongOrders = new LinkedHashMap<>();
        for (Map.Entry<Integer, Integer> item: dto.getOrderedProducts().entrySet()) {
            int productId = item.getKey();
            int quantity = item.getValue();
            if (productId < 1 || quantity <= 0) {
                wrongOrders.put(productId, quantity);
            }
        }
        if (wrongOrders.size() != 0) {
            throw new OrderParamException("Invalid params", wrongOrders.entrySet()
                    .stream()
                    .map(e -> "item" + e.getKey() + "=" + e.getValue())
                    .collect(Collectors.joining(", ")));
        }
    }
}
