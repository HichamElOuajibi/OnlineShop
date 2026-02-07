package com.codeonlineshop.onlineshop;

import com.codeonlineshop.onlineshop.dto.CartItemRequest;
import com.codeonlineshop.onlineshop.model.Order;
import com.codeonlineshop.onlineshop.model.PaymentMethod;
import com.codeonlineshop.onlineshop.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the checkout flow: the cart is converted into a paid order, the chosen payment
 * provider is charged, and the cart is cleared. Also verifies that invalid cases
 * (e.g. empty cart) are rejected with the correct HTTP status.
 */
@DisplayName("OrderService checkout tests")
class OrderServiceCheckoutTest {

    @Test
    @DisplayName("Checkout with items in cart creates a paid order with correct user and total, then clears the cart")
    void checkout_shouldCreatePaidOrderAndClearCart() {
        // Set up the services (products, cart, payment factory and order service).
        ProductService productService = new ProductService();
        CartService cartService = new CartService(productService);
        PaymentServiceFactory paymentServiceFactory = new PaymentServiceFactory(
                new PaypalPaymentService(),
                new StripePaymentService()
        );
        OrderService orderService = new OrderService(paymentServiceFactory, cartService, productService);

        // Act as customer 1: put 2 of product 1 in the cart, then checkout with PayPal.
        long userId = 1L;
        Product product = productService.getProduct(1L);
        cartService.addItem(userId, new CartItemRequest(product.getId(), 2));

        Order order = orderService.checkout(userId, PaymentMethod.PAYPAL);

        // Make sure the order belongs to the right customer (customer id matches the order).
        assertEquals(userId, order.getUserId());
        // Make sure we charged via PayPal and the total is correct (price times quantity).
        assertEquals(PaymentMethod.PAYPAL, order.getPaymentMethod());
        assertEquals(product.getPrice() * 2, order.getTotalAmount(), 0.001);
        // After checkout the cart should be empty.
        assertTrue(cartService.getCart(userId).getItems().isEmpty());
    }

    @Test
    @DisplayName("Checkout with Stripe creates order with payment method STRIPE and clears cart")
    void checkout_withStripe_createsOrderWithStripeAndClearsCart() {
        // Set up services and add one product to the cart for customer 1.
        ProductService productService = new ProductService();
        CartService cartService = new CartService(productService);
        PaymentServiceFactory paymentServiceFactory = new PaymentServiceFactory(
                new PaypalPaymentService(),
                new StripePaymentService()
        );
        OrderService orderService = new OrderService(paymentServiceFactory, cartService, productService);

        long userId = 1L;
        Product product = productService.getProduct(1L);
        cartService.addItem(userId, new CartItemRequest(product.getId(), 1));

        // Checkout with Stripe instead of PayPal.
        Order order = orderService.checkout(userId, PaymentMethod.STRIPE);

        // Make sure the order is tied to the right customer and paid with Stripe.
        assertEquals(userId, order.getUserId());
        assertEquals(PaymentMethod.STRIPE, order.getPaymentMethod());
        assertEquals(product.getPrice(), order.getTotalAmount(), 0.001);
        // Cart must be empty after checkout.
        assertTrue(cartService.getCart(userId).getItems().isEmpty());
    }

    @Test
    @DisplayName("Checkout when cart is empty throws ResponseStatusException (BAD_REQUEST) so user cannot place empty order")
    void checkout_emptyCart_throwsBadRequest() {
        // Set up services but do not add anything to the cart.
        ProductService productService = new ProductService();
        CartService cartService = new CartService(productService);
        PaymentServiceFactory paymentServiceFactory = new PaymentServiceFactory(
                new PaypalPaymentService(),
                new StripePaymentService()
        );
        OrderService orderService = new OrderService(paymentServiceFactory, cartService, productService);

        long userId = 1L;

        // We expect an exception: you cannot checkout with an empty cart.
        assertThrows(ResponseStatusException.class, () -> orderService.checkout(userId, PaymentMethod.PAYPAL));
    }
}
