package ru.clevertec.task.utils.serializers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ru.clevertec.task.exceptions.OrderParamException;

import java.io.IOException;

public class PriceDeserializer extends StdDeserializer<Integer> {
    private static final String PRICE_PATTERN = "\\d+\\.\\d{0,2}";
    public PriceDeserializer() {
        super(Integer.class);
    }

    protected PriceDeserializer(Class<Integer> vc) {
        super(vc);
    }

    @Override
    public Integer deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        String strPrice = p.getText();
        if (!strPrice.matches(PRICE_PATTERN)) {
            throw new OrderParamException(strPrice, "invalid value for price");

        }
        int price = (int) Double.parseDouble(strPrice) * 100;
        return price;
    }
}
