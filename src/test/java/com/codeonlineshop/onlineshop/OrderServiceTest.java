package com.codeonlineshop.onlineshop;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Test
    void placeOrder_shouldCallProcessPayment() {
        // Arrange: create a fake PaymentService
        PaymentService paymentService = mock(PaymentService.class);
        OrderService orderService = new OrderService(paymentService);

        // Act: place the order
        orderService.placeOrder();

        // Assert: verify payment was processed with amount 10
        verify(paymentService).processPayment(10);
    }
}
