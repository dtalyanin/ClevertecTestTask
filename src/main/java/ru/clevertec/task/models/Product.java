package ru.clevertec.task.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ru.clevertec.task.utils.deserializers.PriceDeserializer;
import ru.clevertec.task.utils.serializers.PriceSerializer;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Product representation
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @JsonIgnore
    private Integer id;
    @NotNull
    @Pattern(regexp = "([A-Z]|\\d)[\\p{Alpha}\\d,. %-]+", message = "first letter must be capital")
    @Length(min = 1, max = 100)
    private String name;
    @NotBlank
    @Length(min = 1, max = 20)
    private String measure;
    @NotNull
    @Min(value = 1, message = "price for product should be more than 0")
    @JsonSerialize(using = PriceSerializer.class)
    @JsonDeserialize(using = PriceDeserializer.class)
    private Integer price;
    private boolean promotional;
}