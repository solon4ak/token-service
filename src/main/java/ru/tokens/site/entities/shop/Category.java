package ru.tokens.site.entities.shop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author solon4ak
 */
public class Category implements Serializable {

    private long categoryId;
    private String categoryName;
    private List<Product> items = new ArrayList<>();

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

    public List<Product> getItems() {
        return items;
    }

    public void setItems(List<Product> items) {
        this.items = items;
    }    

    public Product addItem(Product item) {
        this.getItems().add(item);
        return item;
    }

    public Product removeItem(Product item) {
        this.getItems().remove(item);
        item.setCategory(null);
        return item;
    }
    
    public int getItemsNumber() {
        return this.getItems().size();
    }

    @Override
    public String toString() {
        return categoryName;
    }
}
