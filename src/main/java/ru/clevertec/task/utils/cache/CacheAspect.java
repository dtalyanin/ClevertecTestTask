package ru.clevertec.task.utils.cache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.task.models.Product;
import ru.clevertec.task.utils.factories.CacheFactory;

@Aspect
@Component
public class CacheAspect {

    private final Cache<Product> cache;

    public CacheAspect(@Autowired CacheFactory factory) {
        cache = factory.getCacheImplementation();
    }

    @Around(value = "execution(* ru.clevertec.task.dao.impl.ProductDAOImpl.getProductById(..)) " +
            "&& args(id)", argNames = "jp, id")
    public Product getProduct(ProceedingJoinPoint jp, int id) throws Throwable {
        Product product = cache.get(id);
        if (product == null) {
            product = (Product) jp.proceed(new Object[]{id});
            cache.put(product.getId(), product);
        }
        return product;
    }

    @AfterReturning(pointcut = "execution(* ru.clevertec.task.dao.impl.ProductDAOImpl.addNewProduct(..)) " +
            "&& args(product)", returning = "id", argNames = "product, id")
    public void addProduct(Product product, int id) {
        product.setId(id);
        cache.put(id, product);
    }

    @AfterReturning(pointcut = "execution(* ru.clevertec.task.dao.impl.ProductDAOImpl.updateProduct(..)) " +
            "&& args(id, product)", argNames = "id, product")
    public void updateProduct(int id, Product product) {
        product.setId(id);
        cache.put(id, product);
    }

    @AfterReturning(pointcut = "execution(* ru.clevertec.task.dao.impl.ProductDAOImpl.deleteProductById(..)) " +
            "&& args(id)", argNames = "id")
    public void deleteProduct(int id) {
        cache.delete(id);
    }
}
