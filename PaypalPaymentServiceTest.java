package com.codeonlineshop.onlineshop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Ensures that PaypalPaymentService.processPayment completes without throwing an exception
 * for valid amounts, so it is safe to use at checkout when the user selects PayPal.
 */
@DisplayName("PaypalPaymentService tests")
class PaypalPaymentServiceTest {

    @Test
    @DisplayName("Calling processPayment with a positive amount completes without throwing any exception")
    void processPayment_shouldExecuteWithoutException() {
        // We create the PayPal payment service and call it with an amount of 50.
        PaypalPaymentService service = new PaypalPaymentService();
        // We check that it runs without throwing; so it is safe to use at checkout.
        assertDoesNotThrow(() -> service.processPayment(50.0));
    }
}

