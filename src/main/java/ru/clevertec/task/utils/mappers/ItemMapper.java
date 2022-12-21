package ru.clevertec.task.utils.mappers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.clevertec.task.models.Product;
import ru.clevertec.task.models.Item;
import ru.clevertec.task.utils.PriceHelper;

@Component
public class ItemMapper {
    @Value("${item.discount_percent}")
    private int discount;
    @Value("${item.quantity_to_discount}")
    private int quantityToDiscount;

    public Item getItem(Product product, int quantity) {
        boolean promotional = product.isPromotional() && quantity >= quantityToDiscount;
        Integer discountPercent =  promotional ? discount : null;
        int price = product.getPrice();
        int totalPrice = price * quantity;
        int discountPrice = promotional ? PriceHelper.getPricePercentWithRound(price, discountPercent) * quantity : 0;
        int totalPriceWithDiscount = promotional ? totalPrice - discountPrice : totalPrice;
        return new Item(product.getName(),
                product.getMeasure(),
                price,
                quantity,
                discountPercent,
                promotional,
                totalPrice,
                discountPrice,
                totalPriceWithDiscount);
    }
}
