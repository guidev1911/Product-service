package com.microloja.product_service.service;

import com.microloja.product_service.dto.ProductRequestDTO;
import com.microloja.product_service.dto.ProductResponseDTO;
import com.microloja.product_service.exception.ProductNotFoundException;
import com.microloja.product_service.model.Product;
import com.microloja.product_service.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<ProductResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public ProductResponseDTO save(ProductRequestDTO dto) {
        Product saved = repository.save(toEntity(dto));
        return toResponseDTO(saved);
    }

    public ProductResponseDTO findById(Long id) {
        return toResponseDTO(findEntityById(id));
    }

    public ProductResponseDTO update(Long id, ProductRequestDTO dto) {
        Product product = findEntityById(id);
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        return toResponseDTO(repository.save(product));
    }

    public void delete(Long id) {
        repository.delete(findEntityById(id));
    }

    private Product toEntity(ProductRequestDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        return product;
    }

    private ProductResponseDTO toResponseDTO(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        return dto;
    }
    private Product findEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Produto não encontrado"));
    }
}
