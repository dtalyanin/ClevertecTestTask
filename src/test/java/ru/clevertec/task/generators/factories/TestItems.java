package ru.clevertec.task.generators.factories;

import ru.clevertec.task.generators.builders.TestItemBuilder;
import ru.clevertec.task.models.Item;

public class TestItems {

    public static Item getItemWithoutDiscount() {
        return TestItemBuilder.anItem().build();
    }

    public static Item getPromotionalItemWithDiscount() {
        return TestItemBuilder.anItem()
                .promotional(true)
                .discountPercent(10)
                .discountPrice(100)
                .totalPriceWithDiscount(900)
                .build();
    }

    public static Item getItemWithoutDiscountWithSmallQuantity() {
        return TestItemBuilder.anItem().quantity(2).totalPrice(200).totalPriceWithDiscount(200).build();
    }

    public static Item getItemWithLongName() {
        return TestItemBuilder.anItem().name("Test test test test test").build();
    }

    public static Item getItemWithLongNameAndDiscount() {
        return TestItemBuilder.anItem()
                .name("Test test test test test")
                .promotional(true)
                .discountPercent(10)
                .discountPrice(100)
                .totalPriceWithDiscount(900)
                .build();
    }
}
