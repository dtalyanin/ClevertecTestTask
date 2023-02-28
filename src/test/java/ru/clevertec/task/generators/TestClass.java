package ru.clevertec.task.generators;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.clevertec.task.models.DiscountCard;
import ru.clevertec.task.models.Product;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TestClass {
    private List<Product> products;
    private Integer discountPercent;
    private LocalDateTime time;
    private DiscountCard discountCard;
    private int price;
    private int discount;
    private int totalPrice;
}