package com.microloja.product_service.dto;

import com.microloja.product_service.model.Product;

import java.math.BigDecimal;

public class ProductResponseDTO {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;

    public ProductResponseDTO() {
    }

    public ProductResponseDTO(Long id, String name, String description, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
