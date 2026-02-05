package com.codeonlineshop.onlineshop;

import com.codeonlineshop.onlineshop.model.PaymentMethod;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceFactory {
    private final PaypalPaymentService paypalPaymentService;
    private final StripePaymentService stripePaymentService;

    public PaymentServiceFactory(PaypalPaymentService paypalPaymentService,
                                 StripePaymentService stripePaymentService) {
        this.paypalPaymentService = paypalPaymentService;
        this.stripePaymentService = stripePaymentService;
    }

    /**
     * Selects a payment service implementation by method.
     */
    public PaymentService getService(PaymentMethod method) {
        if (method == null) {
            return paypalPaymentService;
        }
        return switch (method) {
            case STRIPE -> stripePaymentService;
            case PAYPAL -> paypalPaymentService;
        };
    }
}
