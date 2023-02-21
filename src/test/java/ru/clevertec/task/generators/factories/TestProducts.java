package ru.clevertec.task.generators.factories;

import ru.clevertec.task.models.Product;

public class TestProducts {
    public static Product getTestProduct() {
        return new Product(1, "Test product", "1 l.", 150, false);
    }
}
