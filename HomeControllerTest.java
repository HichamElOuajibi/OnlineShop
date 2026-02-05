package com.codeonlineshop.onlineshop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Ensures that the main web routes respond with HTTP 200 and forward to the correct
 * static HTML page. When a user visits the root URL, products page, or cart page,
 * they must see the intended screen without a 404.
 */
@WebMvcTest(HomeController.class)
@DisplayName("HomeController tests")
class HomeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Requesting root path (/) returns OK and forwards to the login page so user sees login first")
    void home_forwardsToLoginPage() throws Exception {
        // When we request the root URL (/), we get a successful response and are sent to the login page.
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/login.html"));
    }

    @Test
    @DisplayName("Requesting /products returns OK and forwards to the product catalog page")
    void products_forwardsToProductCatalog() throws Exception {
        // When we request the products URL, we get a successful response and are sent to the product catalog page.
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/product_catalog.html"));
    }

    @Test
    @DisplayName("Requesting /cart returns OK and forwards to the shopping cart page")
    void cart_forwardsToShoppingCartPage() throws Exception {
        // When we request the cart URL, we get a successful response and are sent to the shopping cart page.
        mockMvc.perform(get("/cart"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/shopping_cart.html"));
    }
}
