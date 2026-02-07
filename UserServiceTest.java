package com.codeonlineshop.onlineshop;

import com.codeonlineshop.onlineshop.dto.LoginRequest;
import com.codeonlineshop.onlineshop.dto.RegisterRequest;
import com.codeonlineshop.onlineshop.model.Role;
import com.codeonlineshop.onlineshop.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests user registration and login. Ensures that after registering, the user can log in
 * with the same credentials and that wrong password is rejected.
 */
@DisplayName("UserService tests")
class UserServiceTest {

    @Test
    @DisplayName("After registering a user, login with same username and password returns the same user with correct role")
    void registerAndLogin_shouldReturnUser() {
        UserService userService = new UserService();
        // We register a new user "alice" with password and role CUSTOMER.
        RegisterRequest registerRequest = new RegisterRequest("alice", "password123", "CUSTOMER");

        User registered = userService.register(registerRequest);
        // Then we log in with the same username and password.
        LoginRequest loginRequest = new LoginRequest("alice", "password123");
        User loggedIn = userService.login(loginRequest);

        // We check that the logged-in user is the same person: same id, same username, and role is CUSTOMER.
        assertEquals(registered.getId(), loggedIn.getId());
        assertEquals("alice", loggedIn.getUsername());
        assertEquals(Role.CUSTOMER, loggedIn.getRole());
    }

    @Test
    @DisplayName("Login with wrong password throws ResponseStatusException so invalid credentials are rejected")
    void login_withWrongPassword_shouldFail() {
        UserService userService = new UserService();
        // We register "bob" with password "secret".
        RegisterRequest registerRequest = new RegisterRequest("bob", "secret", "CUSTOMER");
        userService.register(registerRequest);

        // We try to log in as bob but with the wrong password.
        LoginRequest loginRequest = new LoginRequest("bob", "wrong");

        // We expect an exception: login must fail when the password is wrong.
        assertThrows(ResponseStatusException.class, () -> userService.login(loginRequest));
    }
}
