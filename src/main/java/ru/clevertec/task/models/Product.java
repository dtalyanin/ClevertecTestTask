package ru.clevertec.task.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ru.clevertec.task.services.validators.ProductName;
import ru.clevertec.task.utils.serializers.PriceDeserializer;
import ru.clevertec.task.utils.serializers.PriceSerializer;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product {
    @JsonIgnore
    private Integer id;
    @ProductName
    private String name;
    private String measure;
    @JsonSerialize(using = PriceSerializer.class)
    @JsonDeserialize(using = PriceDeserializer.class)
    private Integer price;
    private boolean promotional;
}
