package com.codeonlineshop.onlineshop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class PaypalPaymentServiceTest {

    @Test
    void processPayment_shouldExecuteWithoutException() {
        PaypalPaymentService service = new PaypalPaymentService();

        assertDoesNotThrow(() -> service.processPayment(50.0));
    }
}

