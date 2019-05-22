package ru.tokens.site.services.shop;

import java.util.List;
import ru.tokens.site.entities.shop.Category;
import ru.tokens.site.entities.shop.Product;

/**
 *
 * @author solon4ak
 */
public interface ProductService {
    
    List<Product> getAll();    
    List<Product> getAllForCategory(Category category);
    Product find(long id);
    Product create(Product product);
    void delete(long id);
    
}
