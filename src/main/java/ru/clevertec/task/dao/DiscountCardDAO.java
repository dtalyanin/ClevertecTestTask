package ru.clevertec.task.dao;

import ru.clevertec.task.models.DiscountCard;

public interface DiscountCardDAO {
    DiscountCard getDiscountCardById(int cardNumber);
}
