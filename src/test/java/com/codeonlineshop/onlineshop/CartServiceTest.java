package com.codeonlineshop.onlineshop;

import com.codeonlineshop.onlineshop.dto.CartItemRequest;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class CartServiceTest {

    @Test
    void addItem_withInvalidQuantity_shouldFail() {
        ProductService productService = new ProductService();
        CartService cartService = new CartService(productService);

        CartItemRequest request = new CartItemRequest(1, 0);

        assertThrows(ResponseStatusException.class, () -> cartService.addItem(1L, request));
    }

    @Test
    void addItem_withInvalidProduct_shouldFail() {
        ProductService productService = new ProductService();
        CartService cartService = new CartService(productService);

        CartItemRequest request = new CartItemRequest(999, 1);

        assertThrows(ResponseStatusException.class, () -> cartService.addItem(1L, request));
    }

    @Test
    void removeItem_missingItem_shouldFail() {
        ProductService productService = new ProductService();
        CartService cartService = new CartService(productService);

        assertThrows(ResponseStatusException.class, () -> cartService.removeItem(1L, 1L));
    }
}
