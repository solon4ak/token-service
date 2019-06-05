package ru.tokens.site.controller.admin.shop;

import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.tokens.site.services.shop.CategoryService;

/**
 *
 * @author solon4ak
 */
@Controller
@RequestMapping("shop")
public class ShopController {
    
    private static final Logger log = LogManager.getLogger("ShopController");    
    
    @Autowired
    private CategoryService categoryService;
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String mainShop(Map<String, Object> model) {
        model.put("categories", categoryService.getAll());
        return "shop/frontend/main";
    }
}
