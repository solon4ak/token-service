package ru.tokens.site.controller.admin.shop;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import ru.tokens.config.PropertyPlaceholderConfig;
import ru.tokens.site.entities.Image;
import ru.tokens.site.entities.shop.Category;
import ru.tokens.site.entities.shop.Product;
import ru.tokens.site.entities.shop.ShoppingCart;
import ru.tokens.site.entities.shop.ShoppingCartItem;
import ru.tokens.site.services.ImageService;
import ru.tokens.site.services.shop.CategoryService;
import ru.tokens.site.services.shop.ProductService;
import ru.tokens.site.services.shop.ShopValidator;

/**
 *
 * @author solon4ak
 */
@Controller
@Import(PropertyPlaceholderConfig.class)
@RequestMapping("shop")
public class ShopController {

    private static final Logger log = LogManager.getLogger("ShopController");

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ImageService imageService;
    
    @Autowired
    private ShopValidator shopValidator;
    
    @Value("${shop.delivery.surcharge}")
    private String surcharge;

    @RequestMapping(value = "main", method = RequestMethod.GET)
    public String mainShop(Map<String, Object> model) {
        return "shop/frontend/main";
    }

    @RequestMapping(value = "category/{catId}", method = RequestMethod.GET)
    public String getProductsForCategory(Map<String, Object> model,
            @PathVariable("catId") Long catId) {

        Category category = categoryService.find(catId);
        if (category == null) {
            category = this.categoryService.getAll().get(0);
        }

        model.put("categories", categoryService.getAll());
        model.put("products", productService.getAllForCategory(category));
        model.put("category", category);
        return "shop/frontend/category";
    }

    @RequestMapping(value = "category/{catId}/product/{productId}", method = RequestMethod.GET)
    public String getProduct(Map<String, Object> model,
            @PathVariable("catId") Long catId, @PathVariable("productId") Long productId) {

        Category category = categoryService.find(catId);
        Product product = productService.find(productId);

        if (category == null || product == null) {
            category = this.categoryService.getAll().get(0);
            model.put("categories", categoryService.getAll());
            model.put("products", productService.getAllForCategory(category));
            model.put("category", category);
            return "shop/frontend/category";
        }

        model.put("product", product);
        model.put("category", category);
        return "shop/frontend/product";
    }

    @RequestMapping(value = "product/image/{imgId}/view", method = RequestMethod.GET)
    public void picture(HttpServletResponse response, final @PathVariable("imgId") Long imgId) {

        final Image image = this.imageService.getImageById(imgId);
        final File imageFile = new File(image.getUrl());

        response.setContentType(image.getContentType());
        response.setContentLength(image.getSize().intValue());
        try (InputStream is = new FileInputStream(imageFile)) {
            IOUtils.copy(is, response.getOutputStream());
        } catch (IOException e) {
            log.error("Could not show picture", e);
        }
    }

    @RequestMapping(value = "product/image/{imgId}/icon", method = RequestMethod.GET)
    public void showIcon(HttpServletResponse response, final @PathVariable("imgId") Long imgId) {

        final Image image = this.imageService.getImageById(imgId);
        final File icon = new File(image.getIconUrl());

        response.setContentType(image.getContentType());
        response.setContentLength(image.getIconSize().intValue());
        try (InputStream is = new FileInputStream(icon)) {
            IOUtils.copy(is, response.getOutputStream());
        } catch (IOException e) {
            log.error("Could not show image icon", e);
        }
    }

    @RequestMapping(value = "product/image/{imgId}/thumbnail", method = RequestMethod.GET)
    public void thumbnail(HttpServletResponse response, final @PathVariable("imgId") Long imgId) {

        final Image image = this.imageService.getImageById(imgId);
        final File imageFile = new File(image.getThumbnailUrl());

        response.setContentType(image.getContentType());
        response.setContentLength(image.getThumbnailSize().intValue());
        try (InputStream is = new FileInputStream(imageFile)) {
            IOUtils.copy(is, response.getOutputStream());
        } catch (IOException e) {
            log.error("Could not show image thumbnail", e);
        }
    }

    @RequestMapping(value = "cart/add/category/{catId}/product/{productId}", method = RequestMethod.GET)
    public View addToCartFromCategory(HttpSession session, @PathVariable("catId") Long catId,
            @PathVariable("productId") Long productId, @RequestParam("src") String src) {

        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");

        if (cart == null) {
            cart = new ShoppingCart();
            session.setAttribute("cart", cart);
        }

        if (productId != null) {
            Product product = this.productService.find(productId);
            cart.addItem(product);
        }

        switch (src) {
            case "prod":
                return new RedirectView("/shop/category/"
                        + catId + "/product/" + productId, true, false);
            case "cat":
            default:
                return new RedirectView("/shop/category/" + catId, true, false);
        }
    }

    @RequestMapping(value = "cart/view", method = RequestMethod.GET)
    public String viewShoppingCart(Map<String, Object> model, HttpSession session) {

        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        if (cart == null) {
            return "shop/frontend/main";
        }

        List<ShoppingCartItem> cartItems = cart.getItems();

        model.put("cartItems", cartItems);
        return "shop/frontend/shp_cart";
    }

    @RequestMapping(value = "cart/update", method = RequestMethod.POST)
    public String updateCart(Map<String, Object> model, HttpServletRequest request) {

        ShoppingCart cart = (ShoppingCart) request.getSession(false).getAttribute("cart");
        if (cart == null) {
            return "shop/frontend/main";
        }
        
        String productId = request.getParameter("productId");
        String quantity = request.getParameter("quantity");

        Product product = this.productService.find(Long.valueOf(productId));

        boolean invalidEntry = this.shopValidator.validateQuantity(productId, quantity);
        
        if (!invalidEntry) {
            cart.update(product, quantity);
        }
        
        List<ShoppingCartItem> cartItems = cart.getItems();

        model.put("cartItems", cartItems);
        return "shop/frontend/shp_cart";
    }
    
    @RequestMapping(value = "cart/clear", method = RequestMethod.GET)
    public View clearCart(HttpSession session) {
        
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        if (cart != null) {
            cart.clear();
        }
        
        return new RedirectView("/shop/cart/view", true, false);
    }
    
    @RequestMapping(value = "checkout", method = RequestMethod.GET)
    public String getCheckoutPage(Map<String, Object> model, HttpSession session) {
        
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        if (cart != null) {
            cart.calculateTotal(surcharge);
        }
        model.put("cart", cart);
        model.put("surcharge", surcharge);
        return "shop/frontend/checkout";
    }
    
}
