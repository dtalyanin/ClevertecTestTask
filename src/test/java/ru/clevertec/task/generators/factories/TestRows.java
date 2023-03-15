package ru.clevertec.task.generators.factories;

import ru.clevertec.task.generators.builders.TestRowBuilder;
import ru.clevertec.task.models.file_items.Row;

import java.util.ArrayList;
import java.util.List;

import static ru.clevertec.task.generators.factories.TestColumns.*;

public class TestRows {
    public static Row getMainRow() {
        return TestRowBuilder.aRow()
                .column(getMainQuantity())
                .column(getMainDescription())
                .column(getMainPrice())
                .column(getMainTotal())
                .build();
    }

    public static Row getSimpleRow() {
        return TestRowBuilder.aRow()
                .column(getQuantity())
                .column(getDescription())
                .column(getPrice())
                .column(getTotal())
                .build();
    }

    public static List<Row> getSplitRows() {
        Row first = TestRowBuilder.aRow()
                .column(getQuantity())
                .column(getFirstPartLongDescription())
                .column(getPrice())
                .column(getTotal())
                .build();
        Row second = TestRowBuilder.aRow()
                .column(getEmptyQuantity())
                .column(getSecondPartLongDescription())
                .column(getEmptyPrice())
                .column(getEmptyTotal())
                .build();
        return List.of(first, second);
    }

    public static Row getDiscountRow() {
        return TestRowBuilder.aRow()
                .column(getEmptyQuantity())
                .column(getDiscountDescription())
                .column(getDiscountPercent())
                .column(getDiscount()).build();
    }

    public static List<Row> getSplitRowsWithDiscount() {
        List<Row> splitRows = new ArrayList<>(getSplitRows());
        splitRows.add(getDiscountRow());
        return splitRows;
    }

    public static Row getTotalPriceRow() {
        return TestRowBuilder.aRow()
                .column(getTotalColumnForReceipt())
                .column(getTotalPriceForReceipt())
                .build();
    }

    public static Row getSubtotalPriceRow() {
        return TestRowBuilder.aRow()
                .column(getSubtotalColumnForReceipt())
                .column(getTotalPriceForReceipt())
                .build();
    }

    public static Row getCardDiscountRow() {
        return TestRowBuilder.aRow()
                .column(getCardDiscountDescription())
                .column(getCardDiscount())
                .build();
    }

    public static Row getTotalPriceWithDiscountCardRow() {
        return TestRowBuilder.aRow()
                .column(getTotalColumnForReceipt())
                .column(getTotalPriceForReceiptWithDiscountCard())
                .build();
    }
}
