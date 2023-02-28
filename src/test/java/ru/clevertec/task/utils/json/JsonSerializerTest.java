package ru.clevertec.task.utils.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.clevertec.task.generators.factories.TestItems;
import ru.clevertec.task.generators.factories.TestProducts;
import ru.clevertec.task.generators.factories.TestReceipts;
import ru.clevertec.task.models.Item;
import ru.clevertec.task.models.Product;
import ru.clevertec.task.models.Receipt;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class JsonSerializerTest {

    private ObjectMapper mapper;
    private JsonSerializer serializer;

    @BeforeEach
    void setUp() {
        mapper = JsonMapper.builder()
                .disable(MapperFeature.USE_ANNOTATIONS)
                .addModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .build();
        serializer = new JsonSerializer();
    }

    @Test
    void checkGetJsonFromObjectWorkCorrectWithReceipt() throws JsonProcessingException {
        Receipt receipt = TestReceipts.getReceiptWithDiscountCard();
        String expected = mapper.writeValueAsString(receipt);
        String actual = serializer.getJsonFromObject(receipt);
        assertEquals(expected, actual);
    }

    @Test
    void checkGetJsonFromObjectWorkCorrectWithItem() throws JsonProcessingException {
        Item item = TestItems.getItemWithoutDiscount();
        String expected = mapper.writeValueAsString(item);
        String actual = serializer.getJsonFromObject(item);
        assertEquals(expected, actual);
    }

    @Test
    void checkGetJsonFromObjectWorkCorrectWithProduct() throws JsonProcessingException {
        Product product = TestProducts.getTestProduct();
        String expected = mapper.writeValueAsString(product);
        String actual = serializer.getJsonFromObject(product);
        assertEquals(expected, actual);
    }
}