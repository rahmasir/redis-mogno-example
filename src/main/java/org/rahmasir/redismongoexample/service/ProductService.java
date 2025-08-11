package org.rahmasir.redismongoexample.service;

import org.rahmasir.redismongoexample.model.Product;
import org.rahmasir.redismongoexample.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Service layer for managing products.
 * This class contains the business logic for product operations and caching.
 */
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final RedissonClient redissonClient;

    @Value("${cache.ttl}")
    private long ttl;

    private static final String CACHE_KEY_PREFIX = "product::";

    /**
     * Creates a new product and stores it in the database.
     *
     * @param product The product to create.
     * @return The created product.
     */
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * Retrieves a product by its ID.
     * It first checks the Redis cache. If the product is not in the cache,
     * it fetches it from MongoDB and stores it in the cache for future requests.
     *
     * @param id The ID of the product to retrieve.
     * @return An Optional containing the product if found, or an empty Optional.
     */
    public Optional<Product> getProductById(String id) {
        String cacheKey = CACHE_KEY_PREFIX + id;
        RBucket<Product> bucket = redissonClient.getBucket(cacheKey);

        // Try to get from cache first
        Product product = bucket.get();
        if (product != null) {
            return Optional.of(product);
        }

        // If not in cache, get from DB
        Optional<Product> productFromDb = productRepository.findById(id);
        productFromDb.ifPresent(p -> {
            // Store in cache with a Time-To-Live (TTL)
            bucket.set(p, ttl, TimeUnit.SECONDS);
        });

        return productFromDb;
    }

    /**
     * Retrieves all products of a specific model.
     * Note: Caching for list results can be more complex (e.g., how to invalidate).
     * For simplicity, this method currently does not cache the results.
     * A HashSet could be used to store IDs of products for a given model for more advanced caching.
     *
     * @param model The model to search for.
     * @return A list of products.
     */
    public List<Product> getProductsByModel(String model) {
        return productRepository.findByModel(model);
    }

    /**
     * Updates an existing product.
     * After updating the product in MongoDB, it invalidates the corresponding cache entry in Redis.
     *
     * @param id The ID of the product to update.
     * @param productDetails The new details for the product.
     * @return The updated product.
     */
    public Product updateProduct(String id, Product productDetails) {
        productDetails.setId(id);
        Product updatedProduct = productRepository.save(productDetails);

        // Invalidate the cache
        String cacheKey = CACHE_KEY_PREFIX + id;
        redissonClient.getBucket(cacheKey).delete();

        return updatedProduct;
    }

    /**
     * Deletes a product by its ID.
     * It also invalidates the cache for the deleted product.
     *
     * @param id The ID of the product to delete.
     */
    public void deleteProduct(String id) {
        productRepository.deleteById(id);

        // Invalidate the cache
        String cacheKey = CACHE_KEY_PREFIX + id;
        redissonClient.getBucket(cacheKey).delete();
    }
}
