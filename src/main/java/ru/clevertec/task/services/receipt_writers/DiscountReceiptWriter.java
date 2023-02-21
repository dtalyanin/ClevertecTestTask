package ru.clevertec.task.services.receipt_writers;

import ru.clevertec.task.models.Receipt;
import ru.clevertec.task.utils.factories.ItemWriterFactory;
import ru.clevertec.task.utils.PriceHelper;

import java.io.PrintWriter;

public class DiscountReceiptWriter extends SimpleReceiptWriter {
    public DiscountReceiptWriter(Receipt receipt, PrintWriter writer, ItemWriterFactory factory) {
        super(receipt, writer, factory);
    }

    @Override
    public void writeFooter() {
        int subtotal = receipt.price();
        String strSubtotal = PriceHelper.getPriceRepresentation(subtotal);
        writeTwoColumnsInRowWithEdgeAlign("SUBTOTAL", strSubtotal);
        int discount = receipt.discount();
        String strDiscount = "-" + PriceHelper.getPriceRepresentation(discount);
        writeTwoColumnsInRowWithEdgeAlign("CARD DISCOUNT, " + receipt.discountPercent() + "%", strDiscount);
        super.writeFooter();
    }
}
