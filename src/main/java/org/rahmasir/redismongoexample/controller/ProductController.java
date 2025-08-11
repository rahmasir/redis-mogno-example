package org.rahmasir.redismongoexample.controller;

import org.rahmasir.redismongoexample.model.Product;
import org.rahmasir.redismongoexample.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing products.
 * This class exposes CRUD endpoints for products.
 */
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * POST /api/products : Create a new product.
     *
     * @param product The product to create.
     * @return The created product.
     */
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    /**
     * GET /api/products/{id} : Get a product by its ID.
     *
     * @param id The ID of the product.
     * @return A ResponseEntity with the product if found, or 404 Not Found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/products/model/{model} : Get all products of a certain model.
     *
     * @param model The model of the products.
     * @return A list of products.
     */
    @GetMapping("/model/{model}")
    public List<Product> getProductsByModel(@PathVariable String model) {
        return productService.getProductsByModel(model);
    }

    /**
     * PUT /api/products/{id} : Update a product.
     *
     * @param id The ID of the product to update.
     * @param productDetails The new product data.
     * @return The updated product.
     */
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable String id, @RequestBody Product productDetails) {
        return productService.updateProduct(id, productDetails);
    }

    /**
     * DELETE /api/products/{id} : Delete a product.
     *
     * @param id The ID of the product to delete.
     * @return A ResponseEntity with 204 No Content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
