package com.codeonlineshop.onlineshop;

import com.codeonlineshop.onlineshop.dto.ProductRequest;
import com.codeonlineshop.onlineshop.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService {
    private final Map<Long, Product> products = new ConcurrentHashMap<>();
    private final AtomicLong productId = new AtomicLong(1);

    public ProductService() {
        create(new ProductRequest("Laptop", "Lightweight laptop for study", 899.99, true));
        create(new ProductRequest("Headphones", "Noise cancelling headphones", 129.99, true));
        create(new ProductRequest("Office Chair", "Ergonomic chair for home office", 199.99, true));
    }

    /**
     * Returns the catalog, optionally filtered by query.
     */
    public List<Product> list(String query) {
        List<Product> result = new ArrayList<>(products.values());
        if (query != null && !query.isBlank()) {
            String needle = query.toLowerCase();
            result.removeIf(product -> !matches(product, needle));
        }
        result.sort(Comparator.comparing(Product::getId));
        return result;
    }

    /**
     * Returns a product by id or throws 404.
     */
    public Product getProduct(long id) {
        Product product = products.get(id);
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        return product;
    }

    /**
     * Creates a product for admin usage.
     */
    public Product create(ProductRequest request) {
        validateRequest(request);
        long id = productId.getAndIncrement();
        Product product = new Product(id, request.name(), request.description(), request.price(), request.inStock());
        products.put(id, product);
        return product;
    }

    /**
     * Updates a product for admin usage.
     */
    public Product update(long id, ProductRequest request) {
        validateRequest(request);
        Product product = getProduct(id);
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setInStock(request.inStock());
        return product;
    }

    /**
     * Deletes a product for admin usage.
     */
    public void delete(long id) {
        if (products.remove(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
    }

    private void validateRequest(ProductRequest request) {
        if (request == null || request.name() == null || request.name().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product name is required");
        }
        if (request.price() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Price must be non-negative");
        }
    }

    private boolean matches(Product product, String needle) {
        return (product.getName() != null && product.getName().toLowerCase().contains(needle))
                || (product.getDescription() != null && product.getDescription().toLowerCase().contains(needle));
    }
}
