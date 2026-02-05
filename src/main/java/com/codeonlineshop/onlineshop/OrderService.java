package com.codeonlineshop.onlineshop;

import com.codeonlineshop.onlineshop.model.Cart;
import com.codeonlineshop.onlineshop.model.CartItem;
import com.codeonlineshop.onlineshop.model.Order;
import com.codeonlineshop.onlineshop.model.OrderItem;
import com.codeonlineshop.onlineshop.model.OrderStatus;
import com.codeonlineshop.onlineshop.model.PaymentMethod;
import com.codeonlineshop.onlineshop.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class OrderService {
    private final PaymentServiceFactory paymentServiceFactory;
    private final CartService cartService;
    private final ProductService productService;
    private final List<Order> orders = new CopyOnWriteArrayList<>();
    private final AtomicLong orderId = new AtomicLong(1);
    private PaymentService paymentServiceOverride;

    @Autowired
    public OrderService(PaymentServiceFactory paymentServiceFactory,
                        CartService cartService,
                        ProductService productService) {
        this.paymentServiceFactory = paymentServiceFactory;
        this.cartService = cartService;
        this.productService = productService;
    }

    OrderService(PaymentService paymentService) {
        this.paymentServiceFactory = null;
        this.cartService = null;
        this.productService = null;
        this.paymentServiceOverride = paymentService;
    }

    /**
     * Legacy demo payment call used by the unit test.
     */
    public void placeOrder() {
        PaymentService service = paymentServiceOverride != null
                ? paymentServiceOverride
                : paymentServiceFactory.getService(PaymentMethod.PAYPAL);
        service.processPayment(10);
    }

    /**
     * Converts the cart into a paid order and clears the cart.
     */
    public Order checkout(long userId, PaymentMethod paymentMethod) {
        if (cartService == null || productService == null) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Order service not initialized");
        }
        Cart cart = cartService.getCart(userId);
        if (cart.getItems().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cart is empty");
        }
        List<OrderItem> items = new ArrayList<>();
        double total = 0;
        for (CartItem item : cart.getItems()) {
            Product product = productService.getProduct(item.getProductId());
            if (!product.isInStock()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product out of stock: " + product.getName());
            }
            OrderItem orderItem = new OrderItem(product.getId(), product.getName(), product.getPrice(), item.getQuantity());
            items.add(orderItem);
            total += orderItem.getLineTotal();
        }

        PaymentService paymentService = paymentServiceFactory.getService(paymentMethod);
        paymentService.processPayment(total);

        Order order = new Order(
                orderId.getAndIncrement(),
                userId,
                items,
                total,
                paymentMethod == null ? PaymentMethod.PAYPAL : paymentMethod,
                OrderStatus.PAID,
                Instant.now()
        );
        orders.add(order);
        cartService.clearCart(userId);
        return order;
    }

    /**
     * Returns all orders for admin review.
     */
    public List<Order> getOrders() {
        return new ArrayList<>(orders);
    }

    /**
     * Returns orders for a specific user.
     */
    public List<Order> getOrdersForUser(long userId) {
        List<Order> result = new ArrayList<>();
        for (Order order : orders) {
            if (order.getUserId() == userId) {
                result.add(order);
            }
        }
        return result;
    }
}
