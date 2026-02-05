package com.codeonlineshop.onlineshop;

import com.codeonlineshop.onlineshop.dto.AuthResponse;
import com.codeonlineshop.onlineshop.dto.LoginRequest;
import com.codeonlineshop.onlineshop.dto.RegisterRequest;
import com.codeonlineshop.onlineshop.model.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Registers a new user and returns the created user info.
     */
    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        User user = userService.register(request);
        return new AuthResponse(user.getId(), user.getUsername(), user.getRole());
    }

    /**
     * Authenticates a user and returns their profile info for the UI.
     */
    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        User user = userService.login(request);
        return new AuthResponse(user.getId(), user.getUsername(), user.getRole());
    }
}
