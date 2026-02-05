package com.codeonlineshop.onlineshop;

import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private PaymentService paymentService;

    public OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * Places an order by delegating to the injected payment service with a fixed amount.
     */
    public void placeOrder() {
        paymentService.processPayment(10);
    }

    /**
     * Replaces the payment service (e.g. to switch from PayPal to Stripe).
     */
    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
