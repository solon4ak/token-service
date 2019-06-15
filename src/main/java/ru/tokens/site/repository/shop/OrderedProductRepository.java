package ru.tokens.site.repository.shop;

import java.util.List;
import ru.tokens.site.entities.shop.OrderedProduct;
import ru.tokens.site.entities.shop.OrderedProductPK;

/**
 *
 * @author solon4ak
 */
public interface OrderedProductRepository {
    
    List<OrderedProduct> findByOrderId(long orderId);
    
}
