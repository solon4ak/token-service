package ru.tokens.site.entities.shop;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import ru.tokens.site.entities.Image;

/**
 *
 * @author solon4ak
 */
public class Product implements Serializable {

    private long productId;
    private String productName;
    private Date createdDate;
    private BigDecimal initialPrice;
    private Date lastUpdated;
    private String description;
    private Set<Image> pictures;
    private Set<Category> category;
    private Collection<OrderedProduct> orderedProductCollection;

    public Product() {
    }

    public Product(long productId) {
        this.productId = productId;
    }

    public Product(String itemName, Date createdDate, BigDecimal initialPrice) {
        this.productName = itemName;
        this.createdDate = createdDate;
        this.initialPrice = initialPrice;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
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

    public Set<Image> getPictures() {
        return pictures;
    }

    public void setPictures(Set<Image> pictures) {
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

    public Set<Category> getCategory() {
        return category;
    }

    public void setCategory(Set<Category> category) {
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

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
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
