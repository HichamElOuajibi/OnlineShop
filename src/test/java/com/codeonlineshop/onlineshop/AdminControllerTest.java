package com.codeonlineshop.onlineshop;

import com.codeonlineshop.onlineshop.dto.ProductRequest;
import com.codeonlineshop.onlineshop.model.Order;
import com.codeonlineshop.onlineshop.model.OrderStatus;
import com.codeonlineshop.onlineshop.model.PaymentMethod;
import com.codeonlineshop.onlineshop.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createProduct_shouldReturnOk() throws Exception {
        ProductRequest request = new ProductRequest("Desk", "Office desk", 120.0, true);
        Product product = new Product(1L, "Desk", "Office desk", 120.0, true);
        when(productService.create(request)).thenReturn(product);

        mockMvc.perform(post("/api/admin/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void updateProduct_shouldReturnOk() throws Exception {
        ProductRequest request = new ProductRequest("Desk", "Office desk", 150.0, true);
        Product product = new Product(1L, "Desk", "Office desk", 150.0, true);
        when(productService.update(1L, request)).thenReturn(product);

        mockMvc.perform(put("/api/admin/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteProduct_shouldReturnOk() throws Exception {
        doNothing().when(productService).delete(2L);

        mockMvc.perform(delete("/api/admin/products/2"))
                .andExpect(status().isOk());
    }

    @Test
    void getOrders_shouldReturnOk() throws Exception {
        Order order = new Order(1L, 1L, List.of(), 50.0, PaymentMethod.PAYPAL, OrderStatus.PAID, Instant.now());
        when(orderService.getOrders()).thenReturn(List.of(order));

        mockMvc.perform(get("/api/admin/orders"))
                .andExpect(status().isOk());
    }
}
