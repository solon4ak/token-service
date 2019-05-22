package ru.tokens.site.services.shop;

import java.util.List;
import ru.tokens.site.entities.shop.Category;

/**
 *
 * @author solon4ak
 */
public interface CategoryService {
    
    List<Category> getAll();
    Category find(long id);
    Category create(Category category);
    void delete(long id);
    
    boolean checkCatNameForDuplications(String name);
}
