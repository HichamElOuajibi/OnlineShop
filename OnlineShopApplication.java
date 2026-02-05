package com.codeonlineshop.onlineshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class OnlineShopApplication {

    /**
     * Starts the Spring Boot application, then gets OrderService from the context
     * and runs a sample placeOrder() for demonstration.
     */
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(OnlineShopApplication.class, args);
        var orderservice = context.getBean(OrderService.class);
        orderservice.placeOrder();
    }

}
