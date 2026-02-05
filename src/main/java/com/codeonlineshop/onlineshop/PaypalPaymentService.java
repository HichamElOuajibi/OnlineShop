package com.codeonlineshop.onlineshop;

import org.springframework.stereotype.Service;

@Service
public class PaypalPaymentService implements PaymentService {

    @Override
    public void processPayment(double amount) {
        System.out.println("Paypal Payment Service");
        System.out.println("Amount: " + amount);
    }
}
