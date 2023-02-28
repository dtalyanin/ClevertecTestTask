package ru.clevertec.task.utils.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ru.clevertec.task.exceptions.OrderParamException;

import java.io.IOException;

public class PriceDeserializer extends StdDeserializer<Integer> {

    private static final String PRICE_PATTERN = "\\d+(\\.\\d{0,2})?";

    public PriceDeserializer() {
        this(null);
    }

    protected PriceDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Integer deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String strPrice = p.getValueAsString();
        if (strPrice == null || !strPrice.matches(PRICE_PATTERN)) {
            throw new OrderParamException(strPrice, "invalid value for price");
        }
        return (int) (Double.parseDouble(strPrice) * 100);
    }
}
