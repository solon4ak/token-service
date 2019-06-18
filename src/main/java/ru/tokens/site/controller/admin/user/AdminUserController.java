package ru.tokens.site.controller.admin.user;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.tokens.site.entities.User;
import ru.tokens.site.entities.shop.OrderStatus;
import ru.tokens.site.services.UserService;

/**
 *
 * @author solon4ak
 */
@Controller
@RequestMapping("admin/user")
public class AdminUserController {
    
    private static final Logger log = LogManager.getLogger("AdminUserController");
        
    @Autowired
    private UserService userService;
    
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String listUsers(final Map<String, Object> model) {
        
        final List<User> users = this.userService.getAllUsers();
        Instant now = Instant.now();
        model.put("instant", now.toEpochMilli());
        model.put("users", users);        
        return "admin/user/list";
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
