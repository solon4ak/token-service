package ru.tokens.site.controller.admin.shop;

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
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import ru.tokens.config.PropertyPlaceholderConfig;
import ru.tokens.site.entities.shop.OrderStatus;
import ru.tokens.site.entities.shop.UserOrder;
import ru.tokens.site.services.shop.OrderService;

/**
 *
 * @author solon4ak
 */
@Controller
@Import(PropertyPlaceholderConfig.class)
@RequestMapping("admin/shop/order")
public class AdminOrderController {
    
    private static final Logger log = LogManager.getLogger("AdminOrderController");
    
    @Value("${shop.delivery.surcharge}")
    private String surcharge;
    
    @Autowired
    private OrderService orderService;
    
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String listOrders(final Map<String, Object> model) {
        
        final List<UserOrder> orders = this.orderService.getAllOrders();
        model.put("orders", orders);
        
        return "shop/order/a_list";
    }
    
    @RequestMapping(value = "view/{orderId}", method = RequestMethod.GET)
    public String viewOrder(final Map<String, Object> model, 
            final @PathVariable("orderId") Long orderId) {
        
        Map orderMap = this.orderService.getOrderDetails(orderId);
                
        model.put("customer", orderMap.get("customer"));
        model.put("products", orderMap.get("products"));
        model.put("orderRecord", orderMap.get("orderRecord"));
        model.put("orderedProducts", orderMap.get("orderedProducts"));
        model.put("surcharge", surcharge);
        
        return "shop/order/a_view";
    }
    
    @RequestMapping(value = "edit/{orderId}", method = RequestMethod.GET)
    public String editOrder(final Map<String, Object> model, 
            final @PathVariable("orderId") Long orderId) {
        
        Map orderMap = this.orderService.getOrderDetails(orderId);
        final OrderEditForm form = new OrderEditForm();
        UserOrder order = (UserOrder) orderMap.get("orderRecord");
        form.setOrderStatus(order.getOrderStatus());
        
        model.put("customer", orderMap.get("customer"));
        model.put("products", orderMap.get("products"));
        model.put("orderRecord", orderMap.get("orderRecord"));
        model.put("orderedProducts", orderMap.get("orderedProducts"));
        model.put("surcharge", surcharge);
        model.put("ord_stats", OrderStatus.values());
        model.put("statusForm", form);
        
        return "shop/order/a_edit";
    }
    
    @RequestMapping(value = "edit/{orderId}", method = RequestMethod.POST)
    public View editOrder(final @PathVariable("orderId") Long orderId, 
            final OrderEditForm form) {
        
        UserOrder order = this.orderService.getUserOrder(orderId);
        order.setOrderStatus(form.getOrderStatus());
                
        return new RedirectView("/admin/shop/order/view/" + orderId, true, false);
    }
    
    public static class OrderEditForm {
        private OrderStatus orderStatus;

        public OrderStatus getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(OrderStatus orderStatus) {
            this.orderStatus = orderStatus;
        }        
        
    }
}
