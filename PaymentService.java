package com.codeonlineshop.onlineshop;

/**
 * Contract for processing a payment. Implementations can use PayPal, Stripe, etc.
 */
public interface PaymentService {
    void processPayment(double amount);
}
