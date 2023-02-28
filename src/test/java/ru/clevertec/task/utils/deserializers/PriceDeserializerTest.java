package ru.clevertec.task.utils.deserializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.clevertec.task.exceptions.OrderParamException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PriceDeserializerTest {

    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
        mapper.registerModule(new SimpleModule().addDeserializer(Integer.class, new PriceDeserializer()));
    }

    @ParameterizedTest
    @CsvSource(value = {"2.08, 208", "1.19, 119", "0.01, 1", "1.00, 100", "1.1, 110", "1.00, 100"})
    void checkDeserializeShouldReturnCorrectInt(String strPrice, int expectedPrice) throws IOException {
        int actual = mapper.readValue(strPrice, Integer.class);
        assertEquals(expectedPrice, actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"0.000", "-1.10"})
    void checkDeserializeShouldThrowException(String strPrice) {
        assertThrows(OrderParamException.class, () ->  mapper.readValue(strPrice, Integer.class));
    }
}