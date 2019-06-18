package ru.tokens.site.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.tokens.config.PropertyPlaceholderConfig;
import ru.tokens.site.entities.User;
import ru.tokens.site.entities.shop.UserOrder;
import ru.tokens.site.services.UserService;
import ru.tokens.site.services.shop.OrderService;

/**
 *
 * @author solon4ak
 */
@Controller
@Import(PropertyPlaceholderConfig.class)
@RequestMapping("user/order")
public class OrderController {

    private static final Logger log = LogManager.getLogger("OrderController");
    
    @Value("${shop.delivery.surcharge}")
    private String surcharge;

    @Autowired
    private UserService userService;
    
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String listOrders(Map<String, Object> model, Principal principal) {

        Long userId = Long.valueOf(principal.getName());
        User user = this.userService.findUserById(userId);

        List<UserOrder> orders = this.orderService.getAllOrdersForUser(userId);

        model.put("orders", orders);
        model.put("user", user);
        return "shop/order/list";
    }

    @RequestMapping(value = "view/{orderId}", method = RequestMethod.GET)
    public String viewOrder(Map<String, Object> model, 
            @PathVariable("orderId") Long orderId) {
                
        Map orderMap = this.orderService.getOrderDetails(orderId);
        
        model.put("customer", orderMap.get("customer"));
        model.put("products", orderMap.get("products"));
        model.put("orderRecord", orderMap.get("orderRecord"));
        model.put("orderedProducts", orderMap.get("orderedProducts"));
        model.put("surcharge", surcharge);

        return "shop/order/view";
    }
}
