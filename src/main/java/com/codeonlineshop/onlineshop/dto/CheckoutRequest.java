package com.codeonlineshop.onlineshop.dto;

import com.codeonlineshop.onlineshop.model.PaymentMethod;

public record CheckoutRequest(long userId, PaymentMethod paymentMethod) {
}
