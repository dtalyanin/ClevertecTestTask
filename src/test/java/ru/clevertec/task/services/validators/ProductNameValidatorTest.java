package ru.clevertec.task.services.validators;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(ProductNameValidator.class)
class ProductNameValidatorTest {

    @Autowired
    private ProductNameValidator validator;

    @ParameterizedTest
    @ValueSource(strings = {"Banana", "2 things", "2% milk", "2-having thing", "7 UP", "Milk, 7%"})
    void checkIsValidShouldBeTrue(String value) {
        assertTrue(validator.isValid(value, null));
    }

    @ParameterizedTest
    @ValueSource(strings = {" Banana", "", "     ", "2_thing"})
    void checkIsValidShouldBeFalse(String value) {
        assertFalse(validator.isValid(value, null));
    }

    @Test
    void checkIsValidShouldBeFalseVeryLongName() {
        String longStr = "A".repeat(256);
        assertFalse(validator.isValid(longStr, null));
    }
}