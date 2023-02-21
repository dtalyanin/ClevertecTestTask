package ru.clevertec.task.utils.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ru.clevertec.task.utils.PriceHelper;

import java.io.IOException;

public class PriceSerializer extends StdSerializer<Integer> {

    public PriceSerializer() {
        super(Integer.class);
    }

    public PriceSerializer(Class<Integer> t) {
        super(t);
    }

    @Override
    public void serialize(Integer value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeNumber(PriceHelper.getPriceRepresentation(value));
    }
}
