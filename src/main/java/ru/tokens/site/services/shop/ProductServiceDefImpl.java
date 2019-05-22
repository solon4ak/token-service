package ru.tokens.site.services.shop;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tokens.site.entities.shop.Category;
import ru.tokens.site.entities.shop.Product;
import ru.tokens.site.repository.shop.ProductRepository;

/**
 *
 * @author solon4ak
 */
@Service
public class ProductServiceDefImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAll() {
        return this.productRepository.list();
    }

    @Override
    public List<Product> getAllForCategory(Category category) {
        List<Product> generated = new ArrayList<>();
        for (Product product : this.getAll()) {
            if (product.getCategory().contains(category)) {
                generated.add(product);
            }
        }
        return generated;
    }

    @Override
    public Product find(long id) {
        return this.productRepository.find(id);
    }

    @Override
    public Product create(Product product) {
        if (product.getProductId() < 1) {
            this.productRepository.create(product);
        } else {
            this.productRepository.update(product);
        }
        return product;
    }

    @Override
    public void delete(long id) {
        this.productRepository.delete(id);
    }

}
