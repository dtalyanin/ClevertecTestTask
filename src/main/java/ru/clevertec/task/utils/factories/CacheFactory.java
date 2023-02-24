package ru.clevertec.task.utils.factories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.clevertec.task.models.Product;
import ru.clevertec.task.utils.cache.Cache;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CacheFactory {
    @Value("${cache.implementation}")
    private String cacheChoice;
    Map<String, Cache<Product>> caches;

    @Autowired
    private CacheFactory(List<Cache<Product>> implementations) {
        this.caches = implementations.stream()
                .collect(Collectors.toMap(
                        cache -> cache.getClass().getSimpleName().replace("Cache", ""),
                        Function.identity()));
    }

    public Cache<Product> getCacheImplementation() {
        Cache<Product> cache = caches.get(cacheChoice.toUpperCase());
        if (cache == null) {
            throw new IllegalArgumentException("Wrong value for cache implementation: " + cacheChoice);
        }
        return cache;
    }
}
