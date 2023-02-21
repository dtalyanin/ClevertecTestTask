package ru.clevertec.task.generators.factories;

import ru.clevertec.task.models.file_items.Column;
import ru.clevertec.task.utils.AlignFormatter;
import ru.clevertec.task.utils.DateHelper;

public class TestColumns {
    private static final int QUANTITY_MAX_LENGTH = 3;
    private static final int DESCRIPTION_MAX_LENGTH = 28;
    private static final int PRICE_MAX_LENGTH = 7;
    private static final int TOTAL_MAX_LENGTH = 9;
    private static final int MAX_LENGTH = 50;

    private static final AlignFormatter QUANTITY_FORMATTER = AlignFormatter.CENTER;
    private static final AlignFormatter DESCRIPTION_FORMATTER = AlignFormatter.LEFT;
    private static final AlignFormatter PRICE_FORMATTER = AlignFormatter.RIGHT;
    private static final AlignFormatter TOTAL_FORMATTER = AlignFormatter.RIGHT;

    public static Column getMainQuantity() {
        return new Column("QTY", QUANTITY_MAX_LENGTH, QUANTITY_FORMATTER);
    }

    public static Column getMainDescription() {
        return new Column("DESCRIPTION", DESCRIPTION_MAX_LENGTH, DESCRIPTION_FORMATTER);
    }

    public static Column getMainPrice() {
        return new Column("PRICE", PRICE_MAX_LENGTH, PRICE_FORMATTER);
    }

    public static Column getMainTotal() {
        return new Column("QTY", TOTAL_MAX_LENGTH, TOTAL_FORMATTER);
    }

    public static Column getQuantity() {
        return new Column("10", QUANTITY_MAX_LENGTH, QUANTITY_FORMATTER);
    }

    public static Column getDescription() {
        return new Column("Test, 1 l.", DESCRIPTION_MAX_LENGTH, DESCRIPTION_FORMATTER);
    }

    public static Column getPrice() {
        return new Column("1.00", PRICE_MAX_LENGTH, PRICE_FORMATTER);
    }

    public static Column getTotal() {
        return new Column("10.00", TOTAL_MAX_LENGTH, TOTAL_FORMATTER);
    }

    public static Column getFirstPartLongDescription() {
        return new Column("Test test test test test, 1 ", DESCRIPTION_MAX_LENGTH, DESCRIPTION_FORMATTER);
    }

    public static Column getSecondPartLongDescription() {
        return new Column("l.", DESCRIPTION_MAX_LENGTH, DESCRIPTION_FORMATTER);
    }

    public static Column getEmptyQuantity() {
        return new Column("", QUANTITY_MAX_LENGTH, QUANTITY_FORMATTER);
    }

    public static Column getEmptyPrice() {
        return new Column("", PRICE_MAX_LENGTH, PRICE_FORMATTER);
    }

    public static Column getEmptyTotal() {
        return new Column("", TOTAL_MAX_LENGTH, TOTAL_FORMATTER);
    }

    public static Column getDiscountDescription() {
        return new Column("Promotional discount", DESCRIPTION_MAX_LENGTH, DESCRIPTION_FORMATTER);
    }

    public static Column getDiscountPercent() {
        return new Column("10%", PRICE_MAX_LENGTH, PRICE_FORMATTER);
    }

    public static Column getDiscount() {
        return new Column("-1.00", TOTAL_MAX_LENGTH, TOTAL_FORMATTER);
    }

    public static Column getHeader() {
        return new Column("CASH RECEIPT", MAX_LENGTH, AlignFormatter.CENTER);
    }

    public static Column getDate() {
        return new Column("22.02.2022", MAX_LENGTH, AlignFormatter.RIGHT);
    }

    public static Column getTime() {
        return new Column("06:00:00", MAX_LENGTH, AlignFormatter.RIGHT);
    }

    public static Column getTotalColumnForReceipt() {
        return new Column("TOTAL", 24, AlignFormatter.LEFT);
    }

    public static Column getTotalPriceForReceipt() {
        return new Column("10.00", 25, AlignFormatter.RIGHT);
    }
}
