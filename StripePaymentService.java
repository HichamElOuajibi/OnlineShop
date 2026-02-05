package com.codeonlineshop.onlineshop;

public class StripePaymentService implements PaymentService {

    /**
     * Processes a payment via Stripe. Currently logs the amount to the console.
     */
    @Override
    public void processPayment(double amount) {
        System.out.println("Stripe Payment Service");
        System.out.println("Amount: " + amount);
    }
}
