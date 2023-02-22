package ru.clevertec.task.generators.builders;

import lombok.NoArgsConstructor;
import ru.clevertec.task.models.Item;

@NoArgsConstructor(staticName = "anItem")
public class TestItemBuilder {
    private String name = "Test";
    private String measure = "1 l.";
    private int price = 100;
    private int quantity = 10;
    private Integer discountPercent = null;
    private boolean promotional = false;
    private int totalPrice = 1000;
    private int discountPrice = 0;
    private int totalPriceWithDiscount = 1000;

    public TestItemBuilder name(String name) {
        this.name = name;
        return this;
    }

    public TestItemBuilder measure(String measure) {
        this.measure = measure;
        return this;
    }

    public TestItemBuilder price(int price) {
        this.price = price;
        return this;
    }

    public TestItemBuilder quantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public TestItemBuilder discountPercent(Integer discountPercent) {
        this.discountPercent = discountPercent;
        return this;
    }

    public TestItemBuilder promotional(boolean promotional) {
        this.promotional = promotional;
        return this;
    }

    public TestItemBuilder totalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public TestItemBuilder discountPrice(int discountPrice) {
        this.discountPrice = discountPrice;
        return this;
    }

    public TestItemBuilder totalPriceWithDiscount(int totalPriceWithDiscount) {
        this.totalPriceWithDiscount = totalPriceWithDiscount;
        return this;
    }

    public Item build() {
        return new Item(name,
                measure,
                price,
                quantity,
                discountPercent,
                promotional,
                totalPrice,
                discountPrice,
                totalPriceWithDiscount);
    }
}
