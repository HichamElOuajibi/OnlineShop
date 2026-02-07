package com.codeonlineshop.onlineshop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Verifies that the Spring application context starts successfully and that all
 * required beans (controllers, services, factories) are created and wired correctly.
 * If this test fails, the application will not start in production.
 */
@SpringBootTest
@DisplayName("Application context tests")
class OnlineShopApplicationTests {

    @Test
    @DisplayName("Spring context loads without errors and all beans are available")
    void contextLoads() {
        // When this test runs, Spring starts the application context. If all controllers and services
        // are wired correctly, the test passes. If something is missing or broken, the test fails
        // and the app would not start in production.
    }

}
