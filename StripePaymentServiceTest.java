package com.codeonlineshop.onlineshop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Ensures that StripePaymentService.processPayment completes without throwing an exception
 * for valid amounts, so it is safe to use at checkout when the user selects Stripe.
 */
@DisplayName("StripePaymentService tests")
class StripePaymentServiceTest {

    @Test
    @DisplayName("Calling processPayment with a positive amount completes without throwing any exception")
    void processPayment_shouldExecuteWithoutException() {
        // We create the Stripe payment service and call it with an amount of 75.
        StripePaymentService service = new StripePaymentService();
        // We check that it runs without throwing; so it is safe to use at checkout.
        assertDoesNotThrow(() -> service.processPayment(75.0));
    }
}

