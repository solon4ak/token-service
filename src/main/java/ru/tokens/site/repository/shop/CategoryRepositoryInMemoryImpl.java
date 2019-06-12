package ru.tokens.site.repository.shop;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import ru.tokens.site.entities.shop.Category;

/**
 *
 * @author solon4ak
 */
@Repository
public class CategoryRepositoryInMemoryImpl implements CategoryRepository {
    
    private final Map<Long, Category> categories = new Hashtable<>();
    
    private volatile long CATEGORY_ID_SEQUENCE = 1L;
    private synchronized long getNextId() {
        return this.CATEGORY_ID_SEQUENCE ++;
    }
    
    

    public CategoryRepositoryInMemoryImpl() {
        this.create(new Category("Подвески"));
        this.create(new Category("Пластиковые карточки"));
        this.create(new Category("Браслеты"));
        this.create(new Category("Медальоны"));
    }
    
    @Override
    public List<Category> list() {
        return new ArrayList<>(this.categories.values());
    }

    @Override
    public Category find(long id) {
        return this.categories.get(id);
    }

    @Override
    public Category create(Category category) {
        category.setCategoryId(this.getNextId());
        this.categories.put(category.getCategoryId(), category);
        return category;
    }

    @Override
    public Category update(Category category) {
        this.categories.put(category.getCategoryId(), category);
        return category;
    }

    @Override
    public void delete(long id) {
        this.categories.remove(id);
    }
    
}
