package ru.clevertec.task.generators.factories;

import ru.clevertec.task.models.Receipt;

import java.time.LocalDateTime;

import static ru.clevertec.task.generators.factories.TestItems.getItemWithoutDiscount;

public class TestReceipts {
    public static Receipt getSimpleReceipt() {
        return Receipt.builder()
                .item(getItemWithoutDiscount())
                .dateTime(LocalDateTime.of(2022, 2, 22, 6, 0 ,0))
                .build();
    }

    public static Receipt getReceiptWithDiscountCard() {
        return Receipt.builder()
                .item(getItemWithoutDiscount())
                .discount(10)
                .dateTime(LocalDateTime.of(2022, 2, 22, 6, 0 ,0))
                .build();
    }
}
