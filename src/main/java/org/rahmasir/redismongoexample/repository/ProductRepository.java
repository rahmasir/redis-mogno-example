package org.rahmasir.redismongoexample.repository;

import org.rahmasir.redismongoexample.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data MongoDB repository for the Product collection.
 * This interface provides out-of-the-box CRUD operations for the Product entity.
 * We can also define custom queries here if needed.
 */
@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    /**
     * Finds all products belonging to a specific model.
     * Spring Data automatically implements this method based on its name.
     *
     * @param model The model of the products to find.
     * @return A list of products matching the model.
     */
    List<Product> findByModel(String model);
}
