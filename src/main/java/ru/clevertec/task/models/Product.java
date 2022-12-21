package ru.clevertec.task.models;

import lombok.Data;

@Data
public class Product {
    private Integer id;
    private String name;
    private String measure;
    private Integer price;
    private boolean promotional;
}
