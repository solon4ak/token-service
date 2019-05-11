package ru.tokens.site.entities.shop;

import java.io.Serializable;

/**
 *
 * @author solon4ak
 */
public class OrderedProductPK implements Serializable {
    
    private long userOrderId;
    private long productId;

    public OrderedProductPK() {
    }

    public OrderedProductPK(long userOrderId, long productId) {
        this.userOrderId = userOrderId;
        this.productId = productId;
    }

    public long getUserOrderId() {
        return userOrderId;
    }

    public void setUserOrderId(long userOrderId) {
        this.userOrderId = userOrderId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + (int) (this.userOrderId ^ (this.userOrderId >>> 32));
        hash = 11 * hash + (int) (this.productId ^ (this.productId >>> 32));
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
        final OrderedProductPK other = (OrderedProductPK) obj;
        if (this.userOrderId != other.userOrderId) {
            return false;
        }
        if (this.productId != other.productId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "OrderedProductPK{" + "userOrderId=" + userOrderId + ", productId=" + productId + '}';
    }
    
    
}
