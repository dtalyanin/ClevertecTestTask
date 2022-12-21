package ru.clevertec.task.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountCard {
    private Integer cardNumber;
    private Integer discountPercentage;
}
