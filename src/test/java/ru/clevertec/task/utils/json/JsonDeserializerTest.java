package ru.clevertec.task.utils.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.clevertec.task.generators.TestClass;
import ru.clevertec.task.generators.factories.TestProducts;
import ru.clevertec.task.models.DiscountCard;
import ru.clevertec.task.models.Product;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class JsonDeserializerTest {

    private ObjectMapper mapper;
    private JsonDeserializer deserializer;

    @BeforeEach
    void setUp() {
        mapper = JsonMapper.builder()
                .disable(MapperFeature.USE_ANNOTATIONS)
                .addModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .build();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        deserializer = new JsonDeserializer();
    }

    @Test
    void checkGetObjectFromJsonShouldWorkCorrectWithProduct() throws JsonProcessingException {
        Product expected = TestProducts.getTestProduct();
        String strFromMapper = mapper.writeValueAsString(expected);
        Product actual = deserializer.getObjectFromJson(Product.class, strFromMapper);
        assertEquals(expected, actual);
    }

    @Test
    void checkGetObjectFromJsonShouldWorkCorrectWithDiscountCard() throws JsonProcessingException {
        DiscountCard expected = new DiscountCard(1234, 10);
        String strFromMapper = mapper.writeValueAsString(expected);
        DiscountCard actual = deserializer.getObjectFromJson(DiscountCard.class, strFromMapper);
        assertEquals(expected, actual);
    }

    @Test
    void checkGetObjectFromJsonShouldWorkCorrectWithTestClass() throws JsonProcessingException {
        TestClass expected = new TestClass(
                List.of(TestProducts.getTestProduct(), TestProducts.getTestProduct()),
                10, LocalDateTime.now(), new DiscountCard(1234, 10), 100, 10, 90);
        String strFromMapper = mapper.writeValueAsString(expected);
        TestClass actual = deserializer.getObjectFromJson(TestClass.class, strFromMapper);
        assertEquals(expected, actual);
    }
}