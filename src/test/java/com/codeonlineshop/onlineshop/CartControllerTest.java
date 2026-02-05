package com.codeonlineshop.onlineshop;

import com.codeonlineshop.onlineshop.dto.CartItemRequest;
import com.codeonlineshop.onlineshop.model.Cart;
import com.codeonlineshop.onlineshop.model.CartItem;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@WebMvcTest(CartController.class)
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
    void getCart_shouldReturnOk() throws Exception {
        Cart cart = new Cart(1L);
        cart.getItems().add(new CartItem(1L, 1));
        when(cartService.getCart(1L)).thenReturn(cart);
        when(userService.getUser(1L)).thenReturn(new com.codeonlineshop.onlineshop.model.User(1L, "user", "hash", com.codeonlineshop.onlineshop.model.Role.CUSTOMER));

        mockMvc.perform(get("/api/cart/1"))
                .andExpect(status().isOk());
    }

    @Test
    void addItem_shouldReturnOk() throws Exception {
        Cart cart = new Cart(1L);
        CartItemRequest request = new CartItemRequest(1L, 1);
        when(cartService.addItem(1L, request)).thenReturn(cart);
        when(userService.getUser(1L)).thenReturn(new com.codeonlineshop.onlineshop.model.User(1L, "user", "hash", com.codeonlineshop.onlineshop.model.Role.CUSTOMER));

        mockMvc.perform(post("/api/cart/1/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void removeItem_shouldReturnOk() throws Exception {
        Cart cart = new Cart(1L);
        when(cartService.removeItem(1L, 2L)).thenReturn(cart);
        when(userService.getUser(1L)).thenReturn(new com.codeonlineshop.onlineshop.model.User(1L, "user", "hash", com.codeonlineshop.onlineshop.model.Role.CUSTOMER));

        mockMvc.perform(delete("/api/cart/1/items/2"))
                .andExpect(status().isOk());
    }
}
