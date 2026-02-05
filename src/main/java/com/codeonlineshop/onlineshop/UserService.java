package com.codeonlineshop.onlineshop;

import com.codeonlineshop.onlineshop.dto.LoginRequest;
import com.codeonlineshop.onlineshop.dto.RegisterRequest;
import com.codeonlineshop.onlineshop.model.Role;
import com.codeonlineshop.onlineshop.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {
    private final Map<Long, User> users = new ConcurrentHashMap<>();
    private final Map<String, Long> usernameIndex = new ConcurrentHashMap<>();
    private final AtomicLong userId = new AtomicLong(1);

    public UserService() {
        registerInternal("admin", "admin123", Role.ADMIN);
        registerInternal("customer", "password123", Role.CUSTOMER);
    }

    /**
     * Registers a new user with a role (defaults to CUSTOMER if empty).
     */
    public User register(RegisterRequest request) {
        if (request == null || isBlank(request.username()) || isBlank(request.password())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username and password are required");
        }
        if (usernameIndex.containsKey(request.username().toLowerCase())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }
        Role role = parseRole(request.role());
        return registerInternal(request.username(), request.password(), role);
    }

    /**
     * Validates username/password and returns the user on success.
     */
    public User login(LoginRequest request) {
        if (request == null || isBlank(request.username()) || isBlank(request.password())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username and password are required");
        }
        Long id = usernameIndex.get(request.username().toLowerCase());
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
        User user = users.get(id);
        if (!user.getPasswordHash().equals(hash(request.password()))) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
        return user;
    }

    /**
     * Returns a user by id or throws 404.
     */
    public User getUser(long userId) {
        User user = users.get(userId);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return user;
    }

    private User registerInternal(String username, String password, Role role) {
        long id = userId.getAndIncrement();
        User user = new User(id, username, hash(password), role);
        users.put(id, user);
        usernameIndex.put(username.toLowerCase(), id);
        return user;
    }

    private Role parseRole(String roleValue) {
        if (roleValue == null || roleValue.isBlank()) {
            return Role.CUSTOMER;
        }
        try {
            return Role.valueOf(roleValue.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown role");
        }
    }

    private String hash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashed = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder();
            for (byte b : hashed) {
                builder.append(String.format("%02x", b));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Missing SHA-256 algorithm", e);
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
