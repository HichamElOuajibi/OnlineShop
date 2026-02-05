package com.codeonlineshop.onlineshop;

import com.codeonlineshop.onlineshop.dto.ProductRequest;
import com.codeonlineshop.onlineshop.model.Order;
import com.codeonlineshop.onlineshop.model.Product;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final ProductService productService;
    private final OrderService orderService;

    public AdminController(ProductService productService, OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
    }

    /**
     * Creates a new product (admin operation).
     */
    @PostMapping("/products")
    public Product createProduct(@RequestBody ProductRequest request) {
        return productService.create(request);
    }

    /**
     * Updates an existing product (admin operation).
     */
    @PutMapping("/products/{id}")
    public Product updateProduct(@PathVariable long id, @RequestBody ProductRequest request) {
        return productService.update(id, request);
    }

    /**
     * Deletes a product (admin operation).
     */
    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable long id) {
        productService.delete(id);
    }

    /**
     * Returns all orders for admin review.
     */
    @GetMapping("/orders")
    public List<Order> getOrders() {
        return orderService.getOrders();
    }
}
