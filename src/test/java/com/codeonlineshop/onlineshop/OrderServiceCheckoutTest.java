package com.codeonlineshop.onlineshop;

import com.codeonlineshop.onlineshop.dto.CartItemRequest;
import com.codeonlineshop.onlineshop.model.Order;
import com.codeonlineshop.onlineshop.model.PaymentMethod;
import com.codeonlineshop.onlineshop.model.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderServiceCheckoutTest {

    @Test
    void checkout_shouldCreatePaidOrderAndClearCart() {
        ProductService productService = new ProductService();
        CartService cartService = new CartService(productService);
        PaymentServiceFactory paymentServiceFactory = new PaymentServiceFactory(
                new PaypalPaymentService(),
                new StripePaymentService()
        );
        OrderService orderService = new OrderService(paymentServiceFactory, cartService, productService);

        long userId = 1L;
        Product product = productService.getProduct(1L);
        cartService.addItem(userId, new CartItemRequest(product.getId(), 2));

        Order order = orderService.checkout(userId, PaymentMethod.PAYPAL);

        assertEquals(userId, order.getUserId());
        assertEquals(PaymentMethod.PAYPAL, order.getPaymentMethod());
        assertEquals(product.getPrice() * 2, order.getTotalAmount(), 0.001);
        assertTrue(cartService.getCart(userId).getItems().isEmpty());
    }
}
