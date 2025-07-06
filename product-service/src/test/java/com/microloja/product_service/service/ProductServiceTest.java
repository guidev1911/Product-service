package com.microloja.product_service.service;

import com.microloja.product_service.dto.ProductRequestDTO;
import com.microloja.product_service.dto.ProductResponseDTO;
import com.microloja.product_service.exception.ProductNotFoundException;
import com.microloja.product_service.model.Product;
import com.microloja.product_service.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    private ProductRepository repository;
    private ProductService service;

    @BeforeEach
    void setUp() {
        repository = mock(ProductRepository.class);
        service = new ProductService(repository);
    }

    @Test
    void shouldReturnAllProducts() {
        Product p = new Product(1L, "Produto", "Descrição", new BigDecimal("10.0"));
        when(repository.findAll()).thenReturn(List.of(p));

        List<ProductResponseDTO> products = service.findAll();

        assertEquals(1, products.size());
        assertEquals("Produto", products.get(0).getName());
    }

    @Test
    void shouldSaveProduct() {
        ProductRequestDTO dto = new ProductRequestDTO("Novo", "Desc", new BigDecimal("15.0"));
        Product saved = new Product(1L, "Novo", "Desc", new BigDecimal("15.0"));

        when(repository.save(any())).thenReturn(saved);

        ProductResponseDTO response = service.save(dto);

        assertEquals("Novo", response.getName());
        assertEquals(new BigDecimal("15.0"), response.getPrice());
    }

    @Test
    void shouldFindProductById() {
        Product p = new Product(1L, "Produto", "Descrição", new BigDecimal("10.0"));
        when(repository.findById(1L)).thenReturn(Optional.of(p));

        ProductResponseDTO response = service.findById(1L);

        assertEquals("Produto", response.getName());
    }

    @Test
    void shouldThrowWhenNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> service.findById(1L));
    }

    @Test
    void shouldUpdateProduct() {
        Product existing = new Product(1L, "Old", "Old desc", new BigDecimal("1.0"));
        Product updated = new Product(1L, "New", "New desc", new BigDecimal("99.0"));

        ProductRequestDTO dto = new ProductRequestDTO("New", "New desc", new BigDecimal("99.0"));

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(any())).thenReturn(updated);

        ProductResponseDTO response = service.update(1L, dto);

        assertEquals("New", response.getName());
        assertEquals(new BigDecimal("99.0"), response.getPrice());
    }

    @Test
    void shouldDeleteProduct() {
        Product p = new Product(1L, "Produto", "Descrição", new BigDecimal("10.0"));
        when(repository.findById(1L)).thenReturn(Optional.of(p));

        service.delete(1L);

        verify(repository, times(1)).delete(p);
    }
}
