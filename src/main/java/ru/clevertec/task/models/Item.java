package ru.clevertec.task.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.clevertec.task.utils.serializers.PriceSerializer;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record Item(String name,
                   String measure,
                   @JsonSerialize(using = PriceSerializer.class)int price,
                   int quantity,
                   Integer discountPercent,
                   @JsonIgnore boolean promotional,
                   @JsonSerialize(using = PriceSerializer.class) int totalPrice,
                   @JsonSerialize(using = PriceSerializer.class) int discount,
                   @JsonSerialize(using = PriceSerializer.class) int totalPriceWithDiscount) {

}
