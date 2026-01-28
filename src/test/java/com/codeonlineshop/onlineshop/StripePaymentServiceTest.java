package com.codeonlineshop.onlineshop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class StripePaymentServiceTest {

    @Test
    void processPayment_shouldExecuteWithoutException() {
        StripePaymentService service = new StripePaymentService();

        assertDoesNotThrow(() -> service.processPayment(75.0));
    }
}

