package ru.tokens.site.repository.shop;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import ru.tokens.site.entities.shop.UserOrder;

/**
 *
 * @author solon4ak
 */
@Repository
public class UserOrdersRepositoryInMemoryImpl implements UserOrdersRepository {
    
    private final Map<Long, UserOrder> orders = new Hashtable<>();
    private volatile long ORDER_ID_SEQUENCE = 1L;
    private synchronized long getNextId() {
        return this.ORDER_ID_SEQUENCE ++;
    }

    @Override
    public List<UserOrder> list() {
        return new ArrayList<>(this.orders.values());
    }

    @Override
    public UserOrder find(long id) {
        return this.orders.get(id);
    }

    @Override
    public UserOrder create(UserOrder userOrder) {
        userOrder.setOrderId(this.getNextId());
        this.orders.put(userOrder.getOrderId(), userOrder);
        return userOrder;
    }

    @Override
    public UserOrder update(UserOrder userOrder) {
        this.orders.put(userOrder.getOrderId(), userOrder);
        return userOrder;
    }

    @Override
    public void delete(long id) {
        this.orders.remove(id);
    }
    
}
