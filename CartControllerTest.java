package com.codeonlineshop.onlineshop;

import com.codeonlineshop.onlineshop.dto.CartItemRequest;
import com.codeonlineshop.onlineshop.model.Cart;
import com.codeonlineshop.onlineshop.model.CartItem;
import com.codeonlineshop.onlineshop.model.Role;
import com.codeonlineshop.onlineshop.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests the cart REST API: getting the cart for a user, adding an item, and removing an item.
 * Services are mocked so we only verify that the controller returns OK when the service layer succeeds.
 */
@WebMvcTest(CartController.class)
@DisplayName("CartController API tests")
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/cart/{userId} returns 200 OK when user exists and cart is returned")
    void getCart_shouldReturnOk() throws Exception {
        // We pretend user 1 exists and has a cart with one item in it.
        Cart cart = new Cart(1L);
        cart.getItems().add(new CartItem(1L, 1));
        when(cartService.getCart(1L)).thenReturn(cart);
        when(userService.getUser(1L)).thenReturn(new User(1L, "user", "hash", Role.CUSTOMER));

        // When we ask for the cart for user 1, we get a successful response (200 OK).
        mockMvc.perform(get("/api/cart/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /api/cart/{userId}/items with valid body returns 200 OK when item is added")
    void addItem_shouldReturnOk() throws Exception {
        // We pretend user 1 exists and that adding an item returns their cart.
        Cart cart = new Cart(1L);
        CartItemRequest request = new CartItemRequest(1L, 1);
        when(cartService.addItem(1L, request)).thenReturn(cart);
        when(userService.getUser(1L)).thenReturn(new User(1L, "user", "hash", Role.CUSTOMER));

        // When we add one of product 1 to user 1's cart, we get a successful response (200 OK).
        mockMvc.perform(post("/api/cart/1/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /api/cart/{userId}/items/{itemId} returns 200 OK when item is removed")
    void removeItem_shouldReturnOk() throws Exception {
        // We pretend user 1 exists and that when we remove item 2 from their cart we get the cart back.
        Cart cart = new Cart(1L);
        when(cartService.removeItem(1L, 2L)).thenReturn(cart);
        when(userService.getUser(1L)).thenReturn(new User(1L, "user", "hash", Role.CUSTOMER));

        // When we remove item 2 from user 1's cart, we get a successful response (200 OK).
        mockMvc.perform(delete("/api/cart/1/items/2"))
                .andExpect(status().isOk());
    }
}
