package ru.tokens.site.services.shop;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tokens.site.entities.User;
import ru.tokens.site.entities.shop.OrderedProduct;
import ru.tokens.site.entities.shop.OrderedProductPK;
import ru.tokens.site.entities.shop.Product;
import ru.tokens.site.entities.shop.ShoppingCart;
import ru.tokens.site.entities.shop.ShoppingCartItem;
import ru.tokens.site.entities.shop.UserOrder;
import ru.tokens.site.repository.shop.OrderedProductRepository;
import ru.tokens.site.repository.shop.ProductRepository;
import ru.tokens.site.repository.shop.UserOrdersRepository;

/**
 *
 * @author solon4ak
 */
@Service
public class OrderService {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private UserOrdersRepository ordersRepository;
    
    @Autowired
    private OrderedProductRepository orderedProductRepository;

    public long placeOrder(User user, ShoppingCart cart) {

        try {
            UserOrder order = addOrder(user, cart);
            addOrderedItems(order, cart);
            return order.getOrderId();
        } catch (Exception e) {
//            context.setRollbackOnly();
            return 0;
        }
    }

    private UserOrder addOrder(User user, ShoppingCart cart) {

        // set up customer order
        UserOrder order = new UserOrder();
        order.setUser(user);
        order.setAmount(BigDecimal.valueOf(cart.getTotal()));

        // create confirmation number
        Random random = new Random();
        int i = random.nextInt(999999999);
        order.setConfirmationNumber(i);

//        em.persist(order);
        return order;
    }

    private void addOrderedItems(UserOrder order, ShoppingCart cart) {

//        em.flush();
        List<ShoppingCartItem> items = cart.getItems();

        // iterate through shopping cart and create OrderedProducts
        for (ShoppingCartItem scItem : items) {

            long productId = scItem.getProduct().getProductId();

            // set up primary key object
            OrderedProductPK orderedProductPK = new OrderedProductPK();
            orderedProductPK.setUserOrderId(order.getOrderId());
            orderedProductPK.setProductId(productId);

            // create ordered item using PK object
            OrderedProduct orderedItem = new OrderedProduct(orderedProductPK);

            // set quantity
            orderedItem.setQuantity(scItem.getQuantity());

//            em.persist(orderedItem);
        }
    }

    public Map getOrderDetails(int orderId) {

        Map orderMap = new HashMap();

        // get order
        UserOrder order = this.ordersRepository.find(orderId);

        // get customer
        User user = order.getUser();

        // get all ordered products
        List<OrderedProduct> orderedProducts = orderedProductRepository.findByOrderId(orderId);

        // get product details for ordered items
        List<Product> products = new ArrayList<>();

        for (OrderedProduct op : orderedProducts) {

            Product p = (Product) this.productRepository.find(op.getOrderedProductPK().getProductId());
            products.add(p);
        }

        // add each item to orderMap
        orderMap.put("orderRecord", order);
        orderMap.put("customer", user);
        orderMap.put("orderedProducts", orderedProducts);
        orderMap.put("products", products);

        return orderMap;
    }
}
