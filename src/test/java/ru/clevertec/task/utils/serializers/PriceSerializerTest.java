package ru.clevertec.task.utils.serializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PriceSerializerTest {

    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
        mapper.registerModule(new SimpleModule().addSerializer(new PriceSerializer()));
    }

    @ParameterizedTest(name = "{0} should convert to {1}")
    @CsvSource({"111, 1.11", "0, 0.00", "320, 3.20", "10000, 100.00"})
    void checkSerializeShouldWriteAsDouble(int price, String expectedStrPrice) throws IOException {
        String actual = mapper.writeValueAsString(price);
        assertEquals(expectedStrPrice, actual);
    }
}