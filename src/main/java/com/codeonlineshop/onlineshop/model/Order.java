package com.codeonlineshop.onlineshop.model;

import java.time.Instant;
import java.util.List;

public class Order {
    private final long id;
    private final long userId;
    private final List<OrderItem> items;
    private final double totalAmount;
    private final PaymentMethod paymentMethod;
    private final OrderStatus status;
    private final Instant createdAt;

    public Order(long id, long userId, List<OrderItem> items, double totalAmount,
                 PaymentMethod paymentMethod, OrderStatus status, Instant createdAt) {
        this.id = id;
        this.userId = userId;
        this.items = items;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
