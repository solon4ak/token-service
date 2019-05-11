package ru.tokens.site.entities.shop;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author solon4ak
 */
public class OrderedProduct implements Serializable {

    protected OrderedProductPK orderedProductPK;
    private short quantity;
    private Product product;
    private UserOrder userOrder;

    public OrderedProduct() {
    }

    public OrderedProduct(OrderedProductPK orderedProductPK) {
        this.orderedProductPK = orderedProductPK;
    }

    public OrderedProduct(OrderedProductPK orderedProductPK, short quantity) {
        this.orderedProductPK = orderedProductPK;
        this.quantity = quantity;
    }

    public OrderedProduct(long customerOrderId, long productId) {
        this.orderedProductPK = new OrderedProductPK(customerOrderId, productId);
    }

    public OrderedProductPK getOrderedProductPK() {
        return orderedProductPK;
    }

    public void setOrderedProductPK(OrderedProductPK orderedProductPK) {
        this.orderedProductPK = orderedProductPK;
    }

    public short getQuantity() {
        return quantity;
    }

    public void setQuantity(short quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public UserOrder getUserOrder() {
        return userOrder;
    }

    public void setUserOrder(UserOrder userOrder) {
        this.userOrder = userOrder;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.orderedProductPK);
        hash = 17 * hash + Objects.hashCode(this.product);
        hash = 17 * hash + Objects.hashCode(this.userOrder);
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
        final OrderedProduct other = (OrderedProduct) obj;
        if (!Objects.equals(this.orderedProductPK, other.orderedProductPK)) {
            return false;
        }
        if (!Objects.equals(this.product, other.product)) {
            return false;
        }
        if (!Objects.equals(this.userOrder, other.userOrder)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "OrderedProduct{" + "orderedProductPK=" + orderedProductPK + ", quantity=" + quantity + ", product=" + product + ", userOrder=" + userOrder + '}';
    }
    
    

}
