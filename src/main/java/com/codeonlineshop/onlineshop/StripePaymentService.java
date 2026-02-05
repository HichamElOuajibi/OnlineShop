package com.codeonlineshop.onlineshop;

import org.springframework.stereotype.Service;

@Service
public class StripePaymentService implements PaymentService {
    @Override
    public void processPayment(double amount){
        System.out.println("Stripe Payment Service");
        System.out.println("Amount: " + amount);
    }
}
