package com.codeonlineshop.onlineshop.dto;

public record ProductRequest(String name, String description, double price, boolean inStock) {
}
