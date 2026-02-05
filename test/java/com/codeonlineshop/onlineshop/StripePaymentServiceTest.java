package com.codeonlineshop.onlineshop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("StripePaymentService tests")
class StripePaymentServiceTest {

    @Test
    @DisplayName("processPayment with positive amount runs without throwing exception")
    void processPayment_shouldExecuteWithoutException() {
        StripePaymentService service = new StripePaymentService();
        assertDoesNotThrow(() -> service.processPayment(75.0));
    }

    @Test
    @DisplayName("processPayment with zero amount runs without throwing exception")
    void processPayment_shouldAcceptZeroAmount() {
        StripePaymentService service = new StripePaymentService();
        assertDoesNotThrow(() -> service.processPayment(0));
    }

    @Test
    @DisplayName("processPayment with negative amount runs without throwing exception")
    void processPayment_shouldAcceptNegativeAmount() {
        StripePaymentService service = new StripePaymentService();
        assertDoesNotThrow(() -> service.processPayment(-5.0));
    }
}

