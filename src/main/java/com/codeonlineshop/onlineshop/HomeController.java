package com.codeonlineshop.onlineshop;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Value("${spring.application.name}")
    private String appName;

    /**
     * Serves the main home page UI at "/".
     */
    @GetMapping("/")
    public String home() {
        System.out.println("appName:" + appName);
        return "forward:/login.html";

    }

    /**
     * Serves the product listing UI.
     */
    @GetMapping("/products")
    public String products() {
        return "forward:/product_catalog.html";
    }

    /**
     * Serves the home landing UI.
     */
    @GetMapping("/home")
    public String landingHome() {
        return "forward:/home.html";
    }

    /**
     * Serves the shopping cart UI.
     */
    @GetMapping("/cart")
    public String cart() {
        return "forward:/shopping_cart.html";
    }

    /**
     * Serves the checkout UI.
     */
    @GetMapping("/checkout")
    public String checkout() {
        return "forward:/checkout.html";
    }

    /**
     * Serves the login UI.
     */
    @GetMapping("/login")
    public String login() {
        return "forward:/login.html";
    }

    /**
     * Serves the registration UI.
     */
    @GetMapping("/register")
    public String register() {
        return "forward:/register.html";
    }

    /**
     * Serves the order history UI.
     */
    @GetMapping("/orders")
    public String orders() {
        return "forward:/order_history.html";
    }

    /**
     * Serves the about project UI.
     */
    @GetMapping("/about")
    public String about() {
        return "forward:/about_project.html";
    }


}
