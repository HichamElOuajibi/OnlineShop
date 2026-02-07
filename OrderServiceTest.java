package com.codeonlineshop.onlineshop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

/**
 * Tests the legacy placeOrder() method used on application startup and in demos.
 * Uses a mock PaymentService so we verify the payment call without hitting real PayPal or Stripe.
 */
@DisplayName("OrderService (legacy placeOrder) tests")
class OrderServiceTest {

    @Test
    @DisplayName("When placeOrder is invoked, the injected payment service is called once with the fixed demo amount 10")
    void placeOrder_shouldCallProcessPayment() {
        // We use a fake payment service so we do not call real PayPal or Stripe; we only check it was called.
        PaymentService paymentService = mock(PaymentService.class);
        OrderService orderService = new OrderService(paymentService);

        // When we place an order, the service should charge the fixed demo amount of 10.
        orderService.placeOrder();

        // We check that the payment service was called exactly once with amount 10.
        verify(paymentService).processPayment(10);
    }
}
