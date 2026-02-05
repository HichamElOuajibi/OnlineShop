package com.codeonlineshop.onlineshop;

import com.codeonlineshop.onlineshop.dto.CartItemRequest;
import com.codeonlineshop.onlineshop.model.Cart;
import com.codeonlineshop.onlineshop.model.CartItem;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CartService {
    private final Map<Long, Cart> carts = new ConcurrentHashMap<>();
    private final ProductService productService;

    public CartService(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Returns the user's cart (creates an empty one if missing).
     */
    public Cart getCart(long userId) {
        return carts.computeIfAbsent(userId, Cart::new);
    }

    /**
     * Adds an item to the cart after verifying product exists.
     */
    public Cart addItem(long userId, CartItemRequest request) {
        if (request == null || request.productId() <= 0 || request.quantity() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Valid product and quantity are required");
        }
        productService.getProduct(request.productId());
        Cart cart = getCart(userId);
        synchronized (cart) {
            for (CartItem item : cart.getItems()) {
                if (item.getProductId() == request.productId()) {
                    item.setQuantity(item.getQuantity() + request.quantity());
                    return cart;
                }
            }
            cart.getItems().add(new CartItem(request.productId(), request.quantity()));
        }
        return cart;
    }

    /**
     * Removes a product from the cart.
     */
    public Cart removeItem(long userId, long productId) {
        Cart cart = getCart(userId);
        synchronized (cart) {
            Iterator<CartItem> iterator = cart.getItems().iterator();
            while (iterator.hasNext()) {
                if (iterator.next().getProductId() == productId) {
                    iterator.remove();
                    return cart;
                }
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found in cart");
    }

    /**
     * Clears all items from a cart after checkout.
     */
    public void clearCart(long userId) {
        Cart cart = getCart(userId);
        synchronized (cart) {
            cart.getItems().clear();
        }
    }
}
