package com.codeonlineshop.onlineshop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("PaypalPaymentService tests")
class PaypalPaymentServiceTest {

    @Test
    @DisplayName("processPayment with positive amount runs without throwing exception")
    void processPayment_shouldExecuteWithoutException() {
        PaypalPaymentService service = new PaypalPaymentService();
        assertDoesNotThrow(() -> service.processPayment(50.0));
    }

    @Test
    @DisplayName("processPayment with zero amount runs without throwing exception")
    void processPayment_shouldAcceptZeroAmount() {
        PaypalPaymentService service = new PaypalPaymentService();
        assertDoesNotThrow(() -> service.processPayment(0));
    }

    @Test
    @DisplayName("processPayment with negative amount runs without throwing exception")
    void processPayment_shouldAcceptNegativeAmount() {
        PaypalPaymentService service = new PaypalPaymentService();
        assertDoesNotThrow(() -> service.processPayment(-5.0));
    }
}

