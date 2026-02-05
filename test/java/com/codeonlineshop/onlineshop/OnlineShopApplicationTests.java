package com.codeonlineshop.onlineshop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@DisplayName("Application context and wiring tests")
class OnlineShopApplicationTests {

    @Autowired
    OrderService orderService;

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("OrderService from context can run placeOrder without throwing")
    void orderService_placeOrder_shouldNotThrow() {
        assertDoesNotThrow(() -> orderService.placeOrder());
    }

}
