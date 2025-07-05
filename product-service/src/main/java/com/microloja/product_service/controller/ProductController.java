package com.microloja.product_service.controller;

import com.microloja.product_service.model.Product;
import com.microloja.product_service.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public List<Product> getAll() {
        logger.info("GET /api/products");
        return service.findAll();
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        logger.info("POST /api/products - Body: {}", product);
        return service.save(product);
    }
}
