package com.microloja.product_service.controller;

import com.microloja.product_service.dto.ProductRequestDTO;
import com.microloja.product_service.dto.ProductResponseDTO;
import com.microloja.product_service.service.ProductService;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/products")
@Validated
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService service;

    @GetMapping
    public List<ProductResponseDTO> getAll() {
        logger.info("GET /api/products");
        return service.findAll();
    }

    @PostMapping
    public ProductResponseDTO create(@RequestBody ProductRequestDTO dto) {
        logger.info("POST /api/products - Body: {}", dto);
        return service.save(dto);
    }

    @GetMapping("/{id}")
    public ProductResponseDTO getById(@PathVariable @Min(1) Long id) {
        logger.info("GET /api/products/{}", id);
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public ProductResponseDTO update(@PathVariable @Min(1) Long id, @RequestBody ProductRequestDTO dto) {
        logger.info("PUT /api/products/{} - Body: {}", id, dto);
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Min(1) Long id) {
        logger.info("DELETE /api/products/{}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
