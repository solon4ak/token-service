package ru.tokens.site.repository.shop;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import ru.tokens.site.entities.shop.Product;

/**
 *
 * @author solon4ak
 */
@Repository
public class ProductRepositoryInMemoryImpl implements ProductRepository {

    private final Map<Long, Product> products = new Hashtable<>();
    private volatile long PRODUCT_ID_SEQUENCE = 1L;

    private synchronized long getNextId() {
        return this.PRODUCT_ID_SEQUENCE++;
    }

    @Override
    public List<Product> list() {
        return new ArrayList<>(this.products.values());
    }

    @Override
    public Product find(long id) {
        return this.products.get(id);
    }

    @Override
    public Product create(Product product) {
        product.setProductId(this.getNextId());
        this.products.put(product.getProductId(), product);
        return product;
    }

    @Override
    public Product update(Product product) {
        this.products.put(product.getProductId(), product);
        return product;
    }

    @Override
    public void delete(long id) {
        this.products.remove(id);
    }

}
