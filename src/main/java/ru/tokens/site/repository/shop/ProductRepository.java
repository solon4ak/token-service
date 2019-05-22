package ru.tokens.site.repository.shop;

import java.util.List;
import ru.tokens.site.entities.shop.Product;

/**
 *
 * @author solon4ak
 */
public interface ProductRepository {
    
    List<Product> list();
    Product find(long id);
    Product create(Product product);
    Product update(Product product);
    void delete(long id);
}
