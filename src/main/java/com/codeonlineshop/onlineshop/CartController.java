package com.codeonlineshop.onlineshop;

import com.codeonlineshop.onlineshop.dto.CartItemRequest;
import com.codeonlineshop.onlineshop.model.Cart;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;
    private final UserService userService;

    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    /**
     * Returns the cart for a user (creates one if missing).
     */
    @GetMapping("/{userId}")
    public Cart getCart(@PathVariable long userId) {
        userService.getUser(userId);
        return cartService.getCart(userId);
    }

    /**
     * Adds a product to the user's cart.
     */
    @PostMapping("/{userId}/items")
    public Cart addItem(@PathVariable long userId, @RequestBody CartItemRequest request) {
        userService.getUser(userId);
        return cartService.addItem(userId, request);
    }

    /**
     * Removes a product from the user's cart.
     */
    @DeleteMapping("/{userId}/items/{productId}")
    public Cart removeItem(@PathVariable long userId, @PathVariable long productId) {
        userService.getUser(userId);
        return cartService.removeItem(userId, productId);
    }
}
