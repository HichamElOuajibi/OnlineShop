package com.codeonlineshop.onlineshop;

import com.codeonlineshop.onlineshop.dto.CheckoutRequest;
import com.codeonlineshop.onlineshop.model.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    /**
     * Creates an order from the user's cart and processes payment.
     */
    @PostMapping("/checkout")
    public Order checkout(@RequestBody CheckoutRequest request) {
        userService.getUser(request.userId());
        return orderService.checkout(request.userId(), request.paymentMethod());
    }

    /**
     * Returns orders for a user (or all orders when userId is not provided).
     */
    @GetMapping
    public List<Order> getOrders(@RequestParam(required = false) Long userId) {
        if (userId == null) {
            return orderService.getOrders();
        }
        userService.getUser(userId);
        return orderService.getOrdersForUser(userId);
    }
}
