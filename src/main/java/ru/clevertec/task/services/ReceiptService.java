package ru.clevertec.task.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.task.dao.DiscountCardDAO;
import ru.clevertec.task.dao.ProductDAO;
import ru.clevertec.task.models.*;
import ru.clevertec.task.models.OrderDTO;
import ru.clevertec.task.utils.mappers.ItemMapper;

import java.time.LocalDateTime;

@Service
public class ReceiptService {
    private final ProductDAO productDAO;
    private final DiscountCardDAO discountCardDAO;
    private final ItemMapper mapper;

    @Autowired
    public ReceiptService(ProductDAO productDAO, DiscountCardDAO discountCardDAO, ItemMapper mapper) {
        this.productDAO = productDAO;
        this.discountCardDAO = discountCardDAO;
        this.mapper = mapper;
    }

    public Receipt getReceiptFromDTO(OrderDTO dto) {
        Receipt.ReceiptBuilder builder = Receipt.builder();
        for (Integer productId: dto.getOrderedProducts().keySet()) {
            Product product = productDAO.getProductById(productId);
            int amount = dto.getOrderedProducts().get(productId);
            Item item = mapper.getItem(product, amount);
            builder.item(item);
        }
        if (dto.getDiscount() != null) {
            DiscountCard discountCard = discountCardDAO.getDiscountCardById(dto.getDiscount());
            builder.discount(discountCard.getDiscountPercentage());
        }
        builder.dateTime(LocalDateTime.now());
        return builder.build();
    }
}
