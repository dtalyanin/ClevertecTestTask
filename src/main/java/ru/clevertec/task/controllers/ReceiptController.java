package ru.clevertec.task.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.task.models.Receipt;
import ru.clevertec.task.models.OrderDTO;
import ru.clevertec.task.services.ReceiptService;
import ru.clevertec.task.services.ReceiptFileWriter;
import ru.clevertec.task.utils.DateHelper;
import ru.clevertec.task.utils.mappers.OrderDTOMapper;
import ru.clevertec.task.utils.OrderValidator;

import javax.validation.constraints.*;
import java.util.Map;

@RestController
@Validated
public class ReceiptController {

    private final OrderDTOMapper orderDTOMapper;
    private final ReceiptService service;
    private final ReceiptFileWriter writer;
    private final OrderValidator validator;

    @Autowired
    public ReceiptController(OrderDTOMapper orderDTOMapper, ReceiptService service, ReceiptFileWriter writer, OrderValidator validator) {
        this.orderDTOMapper = orderDTOMapper;
        this.service = service;
        this.writer = writer;
        this.validator = validator;
    }

    @GetMapping(value = "/receipt")
    public ResponseEntity<?> getReceipt(@RequestHeader(value = "Accept", required = false) String header,
                                        @RequestParam @NotEmpty(message = "Request must contain at least one product.")
                          Map<String, String> params) {
        OrderDTO dto = orderDTOMapper.convertMapToOrderDTO(params);
        validator.validate(dto);
        Receipt receipt = service.getReceiptFromDTO(dto);
        String fileName = "receipt_" + DateHelper.getReceiptDateTime(receipt.time()) + ".txt";
        byte[] data = writer.writeReceipt(receipt, fileName);

        if (header != null && header.equals(MediaType.TEXT_PLAIN_VALUE)) {
            HttpHeaders responseHeader = new HttpHeaders();
            responseHeader.setContentType(MediaType.TEXT_PLAIN);
            responseHeader.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
            return new ResponseEntity<>(data, responseHeader, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(receipt, HttpStatus.OK);
        }
    }
}
