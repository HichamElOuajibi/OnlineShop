package com.codeonlineshop.onlineshop.dto;

import com.codeonlineshop.onlineshop.model.Role;

public record AuthResponse(long userId, String username, Role role) {
}
