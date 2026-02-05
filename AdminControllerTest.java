package com.codeonlineshop.onlineshop;

import com.codeonlineshop.onlineshop.dto.ProductRequest;
import com.codeonlineshop.onlineshop.model.Order;
import com.codeonlineshop.onlineshop.model.OrderStatus;
import com.codeonlineshop.onlineshop.model.PaymentMethod;
import com.codeonlineshop.onlineshop.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
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

/**
 * Tests the admin REST API: creating, updating, and deleting products, and listing all orders.
 * Services are mocked so we only verify that the controller returns OK when the service layer succeeds.
 */
@WebMvcTest(AdminController.class)
@DisplayName("AdminController API tests")
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
    @DisplayName("POST /api/admin/products with valid product body returns 200 OK when product is created")
    void createProduct_shouldReturnOk() throws Exception {
        // We pretend that creating a product returns a new product (e.g. "Desk" at 120).
        ProductRequest request = new ProductRequest("Desk", "Office desk", 120.0, true);
        Product product = new Product(1L, "Desk", "Office desk", 120.0, true);
        when(productService.create(request)).thenReturn(product);

        // When we send a request to create this product, we get a successful response (200 OK).
        mockMvc.perform(post("/api/admin/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PUT /api/admin/products/{id} with valid body returns 200 OK when product is updated")
    void updateProduct_shouldReturnOk() throws Exception {
        // We pretend that updating product 1 returns the updated product (e.g. price changed to 150).
        ProductRequest request = new ProductRequest("Desk", "Office desk", 150.0, true);
        Product product = new Product(1L, "Desk", "Office desk", 150.0, true);
        when(productService.update(1L, request)).thenReturn(product);

        // When we send a request to update product 1, we get a successful response (200 OK).
        mockMvc.perform(put("/api/admin/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /api/admin/products/{id} returns 200 OK when product is deleted")
    void deleteProduct_shouldReturnOk() throws Exception {
        // We pretend that deleting product 2 does nothing (no error).
        doNothing().when(productService).delete(2L);

        // When we send a request to delete product 2, we get a successful response (200 OK).
        mockMvc.perform(delete("/api/admin/products/2"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/admin/orders returns 200 OK with list of all orders")
    void getOrders_shouldReturnOk() throws Exception {
        // We pretend there is one order (e.g. order 1 for user 1, paid 50 with PayPal).
        Order order = new Order(1L, 1L, List.of(), 50.0, PaymentMethod.PAYPAL, OrderStatus.PAID, Instant.now());
        when(orderService.getOrders()).thenReturn(List.of(order));

        // When we ask for all orders, we get a successful response (200 OK).
        mockMvc.perform(get("/api/admin/orders"))
                .andExpect(status().isOk());
    }
}
