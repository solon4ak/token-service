package ru.tokens.site.services.shop;

import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import ru.tokens.site.entities.Image;
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
    void deleteImg(Product product, Long imageId);
    List<Image> processImages(List<MultipartFile> pictures)
            throws IOException;
    void deleteProductImages(long productId);
    void deleteProductFromCategories(long productId);
}
