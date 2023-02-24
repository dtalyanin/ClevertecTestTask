package ru.clevertec.task.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.task.models.Product;
import ru.clevertec.task.services.ProductService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/product")
@Validated
public class ProductController {
    private ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable @Min(value = 1, message = "Min ID value is 1") int id) {
        return new ResponseEntity<>(service.getProductById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable @Min(value = 1, message = "Min ID value is 1") int id,
            @RequestBody Product product) {
        return new ResponseEntity<>(service.updateProduct(id, product), HttpStatus.OK);
    }

    @PutMapping(value = "/add")
    public ResponseEntity<String> addNewProduct(@RequestBody @Valid Product product) {
        return new ResponseEntity<>(service.addNewProduct(product), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable @Min(value = 1, message = "Min ID value is 1") int id) {
        return new ResponseEntity<>(service.deleteProductById(id), HttpStatus.OK);
    }
}
