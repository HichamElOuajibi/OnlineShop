package com.codeonlineshop.onlineshop;

import com.codeonlineshop.onlineshop.dto.LoginRequest;
import com.codeonlineshop.onlineshop.dto.RegisterRequest;
import com.codeonlineshop.onlineshop.model.Role;
import com.codeonlineshop.onlineshop.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserServiceTest {

    @Test
    void registerAndLogin_shouldReturnUser() {
        UserService userService = new UserService();
        RegisterRequest registerRequest = new RegisterRequest("alice", "password123", "CUSTOMER");

        User registered = userService.register(registerRequest);
        LoginRequest loginRequest = new LoginRequest("alice", "password123");
        User loggedIn = userService.login(loginRequest);

        assertEquals(registered.getId(), loggedIn.getId());
        assertEquals("alice", loggedIn.getUsername());
        assertEquals(Role.CUSTOMER, loggedIn.getRole());
    }

    @Test
    void login_withWrongPassword_shouldFail() {
        UserService userService = new UserService();
        RegisterRequest registerRequest = new RegisterRequest("bob", "secret", "CUSTOMER");
        userService.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest("bob", "wrong");

        assertThrows(ResponseStatusException.class, () -> userService.login(loginRequest));
    }
}
