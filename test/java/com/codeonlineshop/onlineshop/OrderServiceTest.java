package com.codeonlineshop.onlineshop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;

@DisplayName("OrderService tests")
class OrderServiceTest {

    @Test
    @DisplayName("When placeOrder is called, payment service processPayment is called with amount 10")
    void placeOrder_shouldCallProcessPayment() {
        // Arrange: create a fake PaymentService
        PaymentService paymentService = mock(PaymentService.class);
        OrderService orderService = new OrderService(paymentService);

        // Act: place the order
        orderService.placeOrder();

        // Assert: verify payment was processed with amount 10
        verify(paymentService).processPayment(10);
    }

    @Test
    @DisplayName("When payment service is replaced via setter, placeOrder uses the new service")
    void setPaymentService_shouldUseNewServiceWhenPlacingOrder() {
        PaymentService first = mock(PaymentService.class);
        PaymentService second = mock(PaymentService.class);
        OrderService orderService = new OrderService(first);
        orderService.setPaymentService(second);

        orderService.placeOrder();

        verify(second).processPayment(10);
        verify(first, never()).processPayment(anyDouble());
    }

    @Test
    @DisplayName("When placeOrder is called twice, processPayment is invoked twice with amount 10")
    void placeOrder_calledTwice_shouldCallProcessPaymentTwice() {
        PaymentService paymentService = mock(PaymentService.class);
        OrderService orderService = new OrderService(paymentService);

        orderService.placeOrder();
        orderService.placeOrder();

        verify(paymentService, times(2)).processPayment(10);
    }
}
