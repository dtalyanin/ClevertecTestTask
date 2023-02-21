package ru.clevertec.task.generators.factories;

import ru.clevertec.task.generators.builders.TestOrderDTOBuilder;
import ru.clevertec.task.models.OrderDTO;

public class TestOrdersDTO {

    public static OrderDTO emptyOrder() {
        return TestOrderDTOBuilder.anOrder().build();
    }

    public static OrderDTO orderWithoutDiscount() {
        return TestOrderDTOBuilder.anOrder().OrderedProduct(1, 1).build();
    }

    public static OrderDTO orderWithDiscount() {
        return TestOrderDTOBuilder.anOrder().OrderedProduct(1, 1).discount(1234).build();
    }

    public static OrderDTO orderWithOnlyDiscount() {
        return TestOrderDTOBuilder.anOrder().discount(1234).build();
    }

    public static OrderDTO orderWithIncorrectQuantity() {
        return TestOrderDTOBuilder.anOrder().OrderedProduct(1, 0).OrderedProduct(2, 0).build();
    }

    public static OrderDTO orderWithIncorrectId() {
        return TestOrderDTOBuilder.anOrder().OrderedProduct(0, 1).build();
    }
}
