package com.codeonlineshop.onlineshop.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private final long userId;
    private final List<CartItem> items = new ArrayList<>();

    public Cart(long userId) {
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public List<CartItem> getItems() {
        return items;
    }
}
