package ru.tokens.site.repository.shop;

import java.util.List;
import ru.tokens.site.entities.shop.Category;

/**
 *
 * @author solon4ak
 */
public interface CategoryRepository {
    
    List<Category> list();
    Category find(long id);
    Category create(Category category);
    Category update(Category category);
    void delete(long id);
}
