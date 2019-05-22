package ru.tokens.site.repository.shop;

import java.util.List;
import ru.tokens.site.entities.shop.UserOrder;

/**
 *
 * @author solon4ak
 */
public interface UserOrdersRepository {
    
    List<UserOrder> list();
    UserOrder find(long id);
    UserOrder create(UserOrder userOrder);
    UserOrder update(UserOrder userOrder);
    void delete(long id);
}
