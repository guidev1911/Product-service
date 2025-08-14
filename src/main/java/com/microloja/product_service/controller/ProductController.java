package com.microloja.product_service.controller;

import com.microloja.product_service.dto.ProductRequestDTO;
import com.microloja.product_service.dto.ProductResponseDTO;
import com.microloja.product_service.service.ProductService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/products")
@Validated
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAll() {
        logger.info("GET /products");
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@RequestBody @Valid ProductRequestDTO dto) {
        logger.info("POST /products - Body: {}", dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable Long id) {
        logger.info("GET /products/{}", id);
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable Long id, @RequestBody @Valid ProductRequestDTO dto) {
        logger.info("PUT /products/{} - Body: {}", id, dto);
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("DELETE /products/{}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
