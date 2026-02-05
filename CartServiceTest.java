package com.codeonlineshop.onlineshop;

import com.codeonlineshop.onlineshop.dto.CartItemRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests that CartService rejects invalid input: zero or negative quantity, unknown product id,
 * and removal of an item that is not in the cart. Ensures the API returns proper exceptions
 * instead of corrupting cart state.
 */
@DisplayName("CartService validation tests")
class CartServiceTest {

    @Test
    @DisplayName("Adding an item with quantity zero throws ResponseStatusException because quantity must be positive")
    void addItem_withInvalidQuantity_shouldFail() {
        // We set up the product and cart services.
        ProductService productService = new ProductService();
        CartService cartService = new CartService(productService);
        // We try to add product 1 with quantity zero (which is not allowed).
        CartItemRequest request = new CartItemRequest(1, 0);

        // We expect an exception: you cannot add zero items to the cart.
        assertThrows(ResponseStatusException.class, () -> cartService.addItem(1L, request));
    }

    @Test
    @DisplayName("Adding a product that does not exist (invalid id) throws ResponseStatusException")
    void addItem_withInvalidProduct_shouldFail() {
        // We set up the services. Product 999 does not exist in the catalog.
        ProductService productService = new ProductService();
        CartService cartService = new CartService(productService);
        CartItemRequest request = new CartItemRequest(999, 1);

        // We expect an exception: you cannot add a product that does not exist.
        assertThrows(ResponseStatusException.class, () -> cartService.addItem(1L, request));
    }

    @Test
    @DisplayName("Removing an item that is not in the cart throws ResponseStatusException")
    void removeItem_missingItem_shouldFail() {
        // We set up the services. User 1's cart is empty (we never added anything).
        ProductService productService = new ProductService();
        CartService cartService = new CartService(productService);

        // We expect an exception: you cannot remove an item that is not in the cart.
        assertThrows(ResponseStatusException.class, () -> cartService.removeItem(1L, 1L));
    }
}
