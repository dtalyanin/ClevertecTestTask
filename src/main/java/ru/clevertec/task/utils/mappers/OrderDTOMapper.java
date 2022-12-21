package ru.clevertec.task.utils.mappers;

import org.springframework.stereotype.Component;
import ru.clevertec.task.models.OrderDTO;
import ru.clevertec.task.exceptions.OrderParamException;

import java.util.Map;

@Component
public class OrderDTOMapper {
    private static final String PRODUCT_PARAM = "item";
    private static final String CARD_PARAM = "card";

    public OrderDTO convertMapToOrderDTO(Map<String, String> params) {
        OrderDTO dto = new OrderDTO();
        for (String param : params.keySet()) {
            if (param.matches(PRODUCT_PARAM + "\\d+")) {
                String strProductId = param.substring(PRODUCT_PARAM.length());
                Integer productId = getIntFromStr(strProductId);
                Integer quantity = getIntFromStr(params.get(param));
                dto.addProduct(productId, quantity);
            } else if (param.equals(CARD_PARAM)) {
                Integer discountCardId = getIntFromStr(params.get(param));
                dto.setDiscount(discountCardId);
            }
        }
        return dto;
    }

    private Integer getIntFromStr(String strValue) {
        try {
            return Integer.parseInt(strValue);
        } catch (NumberFormatException e) {
            throw new OrderParamException("Invalid value for number: ", strValue);
        }
    }
}
