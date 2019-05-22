package ru.tokens.site.entities.shop;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import ru.tokens.site.entities.Image;

/**
 *
 * @author solon4ak
 */
public class Product implements Serializable {

    private long productId;
    private String productName;
    private LocalDate createdDate;
    private BigDecimal initialPrice;
    private LocalDate lastUpdated;
    private String description;
    private List<Image> pictures;
    private List<Category> category;
    private Collection<OrderedProduct> orderedProductCollection = new ArrayList<>();

    public Product() {
    }

    public Product(long productId) {
        super();
        this.productId = productId;
        pictures = new LinkedList<>();
        category = new LinkedList<>();
    }

    public Product(String itemName, BigDecimal initialPrice, String description) {
        super();
        this.productName = itemName;
        this.createdDate = LocalDate.now();
        this.initialPrice = initialPrice;
        this.description = description;
        pictures = new LinkedList<>();
        category = new ArrayList<>();
    }
    
    public Product(String itemName, BigDecimal initialPrice, 
            String description, List<Category> categories) {
        super();
        this.productName = itemName;
        this.createdDate = LocalDate.now();
        this.initialPrice = initialPrice;
        this.description = description;
        this.category = categories;
        pictures = new LinkedList<>();
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public BigDecimal getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(BigDecimal initialPrice) {
        this.initialPrice = initialPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Image> getPictures() {
        return pictures;
    }

    public void setPictures(List<Image> pictures) {
        this.pictures = pictures;
    }

    public int getNumberOfPictures() {
        return this.getPictures().size();
    }

    public Image addImage(Image image) {
        this.getPictures().add(image);
        return image;
    }

    public Image removeImage(Image image) {
        this.getPictures().remove(image);
        return image;
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public Category addCategory(Category category) {
        this.getCategory().add(category);
        return category;
    }

    public Category removeCategory(Category category) {
        this.getCategory().remove(category);
        // category.setCategory(null);      // TODO FIX
        return category;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Collection<OrderedProduct> getOrderedProductCollection() {
        return orderedProductCollection;
    }

    public void setOrderedProductCollection(Collection<OrderedProduct> orderedProductCollection) {
        this.orderedProductCollection = orderedProductCollection;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (int) (this.productId ^ (this.productId >>> 32));
        hash = 53 * hash + Objects.hashCode(this.productName);
        hash = 53 * hash + Objects.hashCode(this.createdDate);
        hash = 53 * hash + Objects.hashCode(this.initialPrice);
        hash = 53 * hash + Objects.hashCode(this.description);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        if (this.productId != other.productId) {
            return false;
        }
        if (!Objects.equals(this.productName, other.productName)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.createdDate, other.createdDate)) {
            return false;
        }
        if (!Objects.equals(this.initialPrice, other.initialPrice)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Item{" + "itemId=" + productId + ", itemName=" + productName + '}';
    }
    
    
}
