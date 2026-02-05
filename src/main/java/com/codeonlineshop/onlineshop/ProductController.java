package com.codeonlineshop.onlineshop;

import com.codeonlineshop.onlineshop.model.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Returns the product catalog, optionally filtered by query.
     */
    @GetMapping
    public List<Product> list(@RequestParam(required = false) String q) {
        return productService.list(q);
    }

    /**
     * Returns a single product by id.
     */
    @GetMapping("/{id}")
    public Product getById(@PathVariable long id) {
        return productService.getProduct(id);
    }
}
