package ru.tokens.site.entities.shop;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author solon4ak
 */
public class Category implements Serializable {

    private long categoryId;
    private String categoryName;
    private Set<Product> items;

    private Category parentCategory;
    private Set<Category> subCategories = new HashSet<>();

    public Category() {
    }

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public Category(long categoryId) {
        this.categoryId = categoryId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Set<Product> getItems() {
        return items;
    }

    public void setItems(Set<Product> items) {
        this.items = items;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public Set<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(Set<Category> subCategories) {
        this.subCategories = subCategories;
    }

    public Product addItem(Product item) {
        this.getItems().add(item);
        item.addCategory(this);
        return item;
    }

    public Product removeItem(Product item) {
        this.getItems().remove(item);
        item.setCategory(null);
        return item;
    }

    public Category addCategory(Category category) {
        subCategories.add(category);
        category.setParentCategory(this);
        return category;
    }

    public Category removeCategory(Category category) {
        subCategories.remove(category);
        category.setParentCategory(null);
        return category;
    }

    @Override
    public String toString() {
        return categoryName;
    }
}
