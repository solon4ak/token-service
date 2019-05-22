package ru.tokens.site.repository.shop;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import ru.tokens.site.entities.shop.OrderedProduct;

/**
 *
 * @author solon4ak
 */
@Repository
public class OrderedProductRepositoryInMemoryImpl implements OrderedProductRepository {
    
    private final Map<Long, List<OrderedProduct>> repo = new Hashtable<>();

    @Override
    public List<OrderedProduct> findByOrderId(long orderId) {
        return this.repo.get(orderId);
    }
    
}
