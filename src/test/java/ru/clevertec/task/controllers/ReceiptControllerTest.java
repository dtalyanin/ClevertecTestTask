package ru.clevertec.task.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

import ru.clevertec.task.models.ErrorResponse;
import ru.clevertec.task.models.Item;
import ru.clevertec.task.models.Receipt;
import ru.clevertec.task.services.ReceiptService;
import ru.clevertec.task.services.ReceiptFileWriter;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ReceiptControllerTest {

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReceiptService service;
    @MockBean
    private ReceiptFileWriter writer;


    @Test
    void getReceipt() throws Exception {
        Item item = new Item("Product", "1 l.", 100, 10, null, false, 1000, 0, 1000);
        Receipt receipt = Receipt.builder().item(item).dateTime(LocalDateTime.now()).build();
        byte[] data = receipt.toString().getBytes();
        when(service.getReceiptFromDTO(any())).thenReturn(receipt);
        when(writer.writeReceipt(any(Receipt.class), anyString())).thenReturn(data);
        mockMvc.perform(get("/receipt?item1=10")).andExpect(status().isOk()).andExpect(
                content().json(mapper.writeValueAsString(receipt)));

        ErrorResponse errorResponse = new ErrorResponse("Request must contain at least one product.");
        mockMvc.perform(get("/receipt")).andExpect(status().isBadRequest()).andExpect(
                content().json(mapper.writeValueAsString(errorResponse)));

//        errorResponse = new ErrorResponse("Invalid value for number", "abc");
//        mockMvc.perform(get("/receipt?item1=abc")).andExpect(status().isBadRequest()).andExpect(
//                content().json(mapper.writeValueAsString(errorResponse)));
    }
}