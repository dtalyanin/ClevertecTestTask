package ru.clevertec.task.services.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ProductNameValidator implements ConstraintValidator<ProductName, String> {
    private static final String PATTERN = "([A-Z]|\\d)[\\p{Alpha}\\d,\\. %-]+";
    private static final int MAX_LENGTH_FOR_NAME = 100;

    @Override
    public void initialize(ProductName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return value.length() <= MAX_LENGTH_FOR_NAME && value.matches(PATTERN);
    }
}
