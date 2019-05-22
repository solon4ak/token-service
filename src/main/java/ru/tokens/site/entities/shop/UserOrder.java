package ru.tokens.site.entities.shop;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import ru.tokens.site.entities.User;

/**
 *
 * @author solon4ak
 */
public class UserOrder implements Serializable {

    private long orderId;
    private BigDecimal amount;
    private Date created;
    private long confirmationNumber;
    private Collection<OrderedProduct> orderedProductCollection = new ArrayList<>();;
    private User user;
    private OrderStatus orderStatus;

    public UserOrder() {
    }

    public UserOrder(long orderId) {
        this.orderId = orderId;
    }

    public UserOrder(long orderId, BigDecimal amount, Date created, long confirmationNumber) {
        this.orderId = orderId;
        this.amount = amount;
        this.created = created;
        this.confirmationNumber = confirmationNumber;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public long getConfirmationNumber() {
        return confirmationNumber;
    }

    public void setConfirmationNumber(long confirmationNumber) {
        this.confirmationNumber = confirmationNumber;
    }

    public Collection<OrderedProduct> getOrderedProductCollection() {
        return orderedProductCollection;
    }

    public void setOrderedProductCollection(Collection<OrderedProduct> orderedProductCollection) {
        this.orderedProductCollection = orderedProductCollection;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + (int) (this.orderId ^ (this.orderId >>> 32));
        hash = 11 * hash + Objects.hashCode(this.amount);
        hash = 11 * hash + Objects.hashCode(this.created);
        hash = 11 * hash + (int) (this.confirmationNumber ^ (this.confirmationNumber >>> 32));
        hash = 11 * hash + Objects.hashCode(this.user);
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
        final UserOrder other = (UserOrder) obj;
        if (this.orderId != other.orderId) {
            return false;
        }
        if (this.confirmationNumber != other.confirmationNumber) {
            return false;
        }
        if (!Objects.equals(this.amount, other.amount)) {
            return false;
        }
        if (!Objects.equals(this.created, other.created)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UserOrder{" + "orderId=" + orderId + ", created=" + created + ", orderedProductCollection=" + orderedProductCollection + ", user=" + user + ", orderStatus=" + orderStatus + '}';
    }

    
}
