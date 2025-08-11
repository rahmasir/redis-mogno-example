package org.rahmasir.redismongoexample.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for the Redisson client.
 * This class sets up the connection to the Redis server and defines the codec.
 */
@Configuration
public class RedissonConfig {

    @Value("${redis.host}")
    private String redisHost;

    @Value("${redis.port}")
    private int redisPort;

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() {
        var config = new Config();
        config.useSingleServer()
                .setAddress("redis://" + redisHost + ":" + redisPort);

        // Using JsonJacksonCodec to serialize/deserialize objects to/from JSON.
        // This is generally more flexible and interoperable than Java's default serialization.
        config.setCodec(new JsonJacksonCodec());

        return Redisson.create(config);
    }
}
