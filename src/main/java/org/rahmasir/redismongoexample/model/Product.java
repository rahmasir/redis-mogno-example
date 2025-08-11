package org.rahmasir.redismongoexample.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * Represents a product in the inventory.
 * This class is designed to be flexible to accommodate various product models.
 * The `specs` field is a Map, allowing for arbitrary nested key-value pairs.
 * Implements Serializable to be cacheable by Redis.
 */
@Data
@Document(collection = "products")
public class Product implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String model;
    private String name;
    private double price;

    /**
     * A map to hold flexible, nested specifications for the product.
     * This allows us to store different structures for different product models
     * without changing the database schema.
     * Example: {"processor": "Intel i9", "memory": {"size": "32GB", "type": "DDR5"}}
     */
    private Map<String, Object> specs;

    /**
     * A map for warranty information.
     */
    private Map<String, Object> warranty;
}
