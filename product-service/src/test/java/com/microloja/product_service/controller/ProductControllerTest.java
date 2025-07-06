package com.microloja.product_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microloja.product_service.dto.ProductRequestDTO;
import com.microloja.product_service.dto.ProductResponseDTO;
import com.microloja.product_service.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@Import(ProductControllerTest.MockConfig.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductService productService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public ProductService productService() {
            return Mockito.mock(ProductService.class);
        }
    }

    @Test
    void shouldGetAllProducts() throws Exception {
        ProductResponseDTO dto = new ProductResponseDTO(1L, "Produto", "Desc", new BigDecimal("10.0"));
        when(productService.findAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Produto"));
    }

    @Test
    void shouldCreateProduct() throws Exception {
        ProductRequestDTO request = new ProductRequestDTO("Novo", "Desc", new BigDecimal("15.0"));
        ProductResponseDTO response = new ProductResponseDTO(1L, "Novo", "Desc", new BigDecimal("15.0"));

        when(productService.save(any())).thenReturn(response);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Novo"));
    }

    @Test
    void shouldGetProductById() throws Exception {
        ProductResponseDTO response = new ProductResponseDTO(1L, "Produto", "Desc", new BigDecimal("10.0"));
        when(productService.findById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Produto"));
    }

    @Test
    void shouldUpdateProduct() throws Exception {
        ProductRequestDTO request = new ProductRequestDTO("Atualizado", "Nova desc", new BigDecimal("22.0"));
        ProductResponseDTO response = new ProductResponseDTO(1L, "Atualizado", "Nova desc", new BigDecimal("22.0"));

        when(productService.update(eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Atualizado"));
    }

    @Test
    void shouldDeleteProduct() throws Exception {
        doNothing().when(productService).delete(1L);

        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());
    }
}
