package com.codeonlineshop.onlineshop;

import com.codeonlineshop.onlineshop.model.PaymentMethod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Verifies that PaymentServiceFactory returns the correct payment provider implementation
 * for each payment method. At checkout the factory is used to resolve PayPal or Stripe
 * so the correct gateway is charged; these tests ensure that mapping is correct.
 */
@DisplayName("PaymentServiceFactory tests")
class PaymentServiceFactoryTest {

    private final PaypalPaymentService paypal = new PaypalPaymentService();
    private final StripePaymentService stripe = new StripePaymentService();
    private final PaymentServiceFactory factory = new PaymentServiceFactory(paypal, stripe);

    @Test
    @DisplayName("When payment method is PAYPAL, factory returns the PayPal payment service instance")
    void getService_paypal_returnsPaypalService() {
        // We ask the factory for the service when the customer chose PayPal.
        PaymentService service = factory.getService(PaymentMethod.PAYPAL);
        // We check we get back the same PayPal instance we gave to the factory.
        assertSame(paypal, service);
    }

    @Test
    @DisplayName("When payment method is STRIPE, factory returns the Stripe payment service instance")
    void getService_stripe_returnsStripeService() {
        // We ask the factory for the service when the customer chose Stripe.
        PaymentService service = factory.getService(PaymentMethod.STRIPE);
        // We check we get back the same Stripe instance we gave to the factory.
        assertSame(stripe, service);
    }

    @Test
    @DisplayName("When payment method is null, factory returns PayPal as the default provider")
    void getService_null_returnsPaypalAsDefault() {
        // We ask the factory for the service without specifying a method (null).
        PaymentService service = factory.getService(null);
        // We check we get PayPal as the default so checkout still works.
        assertSame(paypal, service);
    }
}
