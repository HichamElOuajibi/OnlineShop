package com.codeonlineshop.onlineshop.model;

public class OrderItem {
    private final long productId;
    private final String name;
    private final double unitPrice;
    private final int quantity;
    private final double lineTotal;

    public OrderItem(long productId, String name, double unitPrice, int quantity) {
        this.productId = productId;
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.lineTotal = unitPrice * quantity;
    }

    public long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getLineTotal() {
        return lineTotal;
    }
}
