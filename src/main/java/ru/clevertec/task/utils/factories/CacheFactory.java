package ru.clevertec.task.utils.factories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.clevertec.task.exceptions.CacheException;
import ru.clevertec.task.models.Product;
import ru.clevertec.task.utils.cache.Cache;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Factory for getting cache implementation from registered cache beans
 */
@Component
public class CacheFactory {
    @Value("${cache.implementation}")
    private String cacheChoice;
    private Map<String, Cache<Product>> caches;

    @Autowired
    private CacheFactory(List<Cache<Product>> implementations) {
        this.caches = implementations.stream()
                .collect(Collectors.toMap(
                        cache -> cache.getClass().getSimpleName().replace("Cache", ""),
                        Function.identity()));
    }

    /**
     * returns a cache based on values from application.properties
     *
     * @return cache implementation
     * @throws CacheException if cache.implementation property not present or with wrong value
     */
    public Cache<Product> getCacheImplementation() {
        if (cacheChoice == null) {
            throw new CacheException("No value for cache implementation");
        }
        Cache<Product> cache = caches.get(cacheChoice.toUpperCase());
        if (cache == null) {
            throw new CacheException("Wrong value for cache implementation: " + cacheChoice);
        }
        return cache;
    }
}
