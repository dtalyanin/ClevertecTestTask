package ru.clevertec.task.utils.serializers;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import static org.junit.jupiter.api.Assertions.*;

class PriceSerializerTest {

    @Test
    void serialize() throws IOException {
        Writer jsonWriter = new StringWriter();
        JsonGenerator jsonGenerator = new JsonFactory().createGenerator(jsonWriter);
        SerializerProvider serializerProvider = new ObjectMapper().getSerializerProvider();
        PriceSerializer serializer = new PriceSerializer();

        String expected = "1.11";
        serializer.serialize(111, jsonGenerator, serializerProvider);
        jsonGenerator.flush();
        String actual = jsonWriter.toString();
        assertEquals(expected, actual);
    }
}