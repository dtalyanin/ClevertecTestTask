package ru.clevertec.task.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.clevertec.task.utils.PriceHelper;
import ru.clevertec.task.utils.serializers.PriceSerializer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record Receipt(List<Item> items, Integer discountPercent,
                      @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss") LocalDateTime time,
                      @JsonIgnore boolean discountCard,
                      @JsonSerialize(using = PriceSerializer.class) int price,
                      @JsonSerialize(using = PriceSerializer.class) int discount,
                      @JsonSerialize(using = PriceSerializer.class) int totalPrice) {

    public static ReceiptBuilder builder() {
        return new ReceiptBuilder();
    }

    public static class ReceiptBuilder {
        private List<Item> items;
        private Integer discount;
        private LocalDateTime dateTime;

        ReceiptBuilder() {
            this.items = new ArrayList<>();
        }

        public ReceiptBuilder items(List<Item> items) {
            this.items = items;
            return this;
        }

        public ReceiptBuilder item(Item item) {
            this.items.add(item);
            return this;
        }

        public ReceiptBuilder discount(Integer discount) {
            this.discount = discount;
            return this;
        }

        public ReceiptBuilder dateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public Receipt build() {
            boolean promotional = discount != null;
            int price = items.stream().mapToInt(Item::totalPriceWithDiscount).sum();
            int discountPrice = promotional ? PriceHelper.getPricePercentWithRound(items.stream()
                    .filter(item -> !item.promotional())
                    .mapToInt(Item::totalPrice).sum(), discount) : 0;
            int totalPrice = promotional ? price - discountPrice : price;
            return new Receipt(this.items, this.discount, dateTime, promotional, price, discountPrice, totalPrice);
        }
    }
}
