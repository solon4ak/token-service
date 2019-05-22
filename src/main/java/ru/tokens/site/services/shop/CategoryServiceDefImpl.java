package ru.tokens.site.services.shop;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tokens.site.entities.shop.Category;
import ru.tokens.site.repository.shop.CategoryRepository;

/**
 *
 * @author solon4ak
 */
@Service
public class CategoryServiceDefImpl implements CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAll() {
        return this.categoryRepository.list();
    }

    @Override
    public Category find(long id) {
        return this.categoryRepository.find(id);
    }

    @Override
    public Category create(Category category) {
        if (category.getCategoryId() < 1) {
            this.categoryRepository.create(category);
        } else {
            this.categoryRepository.update(category);
        }
        return category;
    }

    @Override
    public void delete(long id) {
        this.categoryRepository.delete(id);
    }
    
    /**
     * Check creating or editing category for name duplications with existing categories
     * @param name - new category name
     * @return true if not duplicate
     */
    @Override
    public boolean checkCatNameForDuplications(String name) {
        for (Category category : this.getAll()) {
            if (name.equalsIgnoreCase(category.getCategoryName())) {
                return false;
            }
        }
        return true;
    }
}
