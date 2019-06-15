package ru.tokens.site.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import ru.tokens.config.PropertyPlaceholderConfig;
import ru.tokens.site.entities.Image;
import ru.tokens.site.entities.User;
import ru.tokens.site.entities.UserPrincipal;
import ru.tokens.site.entities.shop.Category;
import ru.tokens.site.entities.shop.Product;
import ru.tokens.site.entities.shop.ShoppingCart;
import ru.tokens.site.entities.shop.ShoppingCartItem;
import ru.tokens.site.services.AuthenticationService;
import ru.tokens.site.services.ImageService;
import ru.tokens.site.services.UserService;
import ru.tokens.site.services.shop.CategoryService;
import ru.tokens.site.services.shop.OrderService;
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

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @Value("${shop.delivery.surcharge}")
    private String surcharge;

    @RequestMapping(value = "main", method = RequestMethod.GET)
    public String mainShop(final Map<String, Object> model) {
        return "shop/frontend/main";
    }

    @RequestMapping(value = "category/{catId}", method = RequestMethod.GET)
    public String getProductsForCategory(final Map<String, Object> model,
            @PathVariable("catId") final Long catId) {

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
    public String getProduct(final Map<String, Object> model,
            @PathVariable("catId") final Long catId,
            @PathVariable("productId") final Long productId) {

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
    public View addToCartFromCategory(HttpSession session, @PathVariable("catId") final Long catId,
            @PathVariable("productId") final Long productId, @RequestParam("src") final String src) {

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
    public String viewShoppingCart(final Map<String, Object> model, HttpSession session) {

        final ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        if (cart == null) {
            return "shop/frontend/main";
        }

        final List<ShoppingCartItem> cartItems = cart.getItems();

        model.put("cartItems", cartItems);
        return "shop/frontend/shp_cart";
    }

    @RequestMapping(value = "cart/update", method = RequestMethod.POST)
    public String updateCart(final Map<String, Object> model, HttpServletRequest request) {

        final ShoppingCart cart = (ShoppingCart) request.getSession(false).getAttribute("cart");
        if (cart == null) {
            return "shop/frontend/main";
        }

        final String productId = request.getParameter("productId");
        final String quantity = request.getParameter("quantity");

        final Product product = this.productService.find(Long.valueOf(productId));

        final boolean invalidEntry = this.shopValidator.validateQuantity(productId, quantity);

        if (!invalidEntry) {
            cart.update(product, quantity);
        }

        final List<ShoppingCartItem> cartItems = cart.getItems();

        model.put("cartItems", cartItems);
        return "shop/frontend/shp_cart";
    }

    @RequestMapping(value = "cart/clear", method = RequestMethod.GET)
    public View clearCart(HttpSession session) {

        final ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        if (cart != null) {
            cart.clear();
        }

        return new RedirectView("/shop/cart/view", true, false);
    }

    @RequestMapping(value = "checkout", method = RequestMethod.GET)
    public String getCheckoutPage(final Map<String, Object> model, HttpSession session) {

        final ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        if (cart != null) {
            cart.calculateTotal(surcharge);
        }

        if (UserPrincipal.getPrincipal(session) != null) {
            model.put("authorised", true);
        } else {
            model.put("loginFailed", false);
            model.put("loginForm", new LoginForm());
        }

        model.put("cart", cart);
        model.put("surcharge", surcharge);

        return "shop/frontend/checkout";
    }

    @RequestMapping(value = "shop_login", method = RequestMethod.POST)
    public ModelAndView getCheckoutPage(final Map<String, Object> model, HttpSession session,
            HttpServletRequest request, LoginForm lForm) {

        if (UserPrincipal.getPrincipal(session) != null) {
            model.put("authorised", true);
        } else {
            Principal principal = this.authenticationService
                    .authenticate(lForm.getEmail(), lForm.getPassword());

            if (principal == null) {
                log.warn("Principal == null");
                lForm.setPassword(null);
                model.put("loginFailed", true);
                model.put("loginForm", lForm);
                model.put("message", "Логин или пароль не совпадают.");
                return new ModelAndView(new RedirectView("/shop/checkout", true, true));
            }

            UserPrincipal.setPrincipal(session, principal);
            request.changeSessionId();
            model.put("authorised", true);

        }

        final ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        if (cart != null) {
            cart.calculateTotal(surcharge);
        }

        model.put("cart", cart);
        model.put("surcharge", surcharge);

        return new ModelAndView("shop/frontend/checkout");
    }

    @RequestMapping(value = "purchase", method = RequestMethod.GET)
    public ModelAndView getPurchase(final Map<String, Object> model, HttpSession session) {

        final Principal principal = (Principal) session.getAttribute("ru.tkn.user.principal");
        if (principal == null) {
            return new ModelAndView(new RedirectView("/shop/checkout", true, false));
        }

        final Long userId = Long.valueOf(principal.getName());
        final User user = this.userService.findUserById(userId);

        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");

        if (cart == null) {
            return new ModelAndView(new RedirectView("/shop/main", true, false));
        }

        final long orderId = this.orderService.placeOrder(user, cart);
        cart.clear();
        cart = null;
//        session.invalidate();

        final Map orderMap = this.orderService.getOrderDetails(orderId);

        model.put("customer", orderMap.get("customer"));
        model.put("products", orderMap.get("products"));
        model.put("orderRecord", orderMap.get("orderRecord"));
        model.put("orderedProducts", orderMap.get("orderedProducts"));
        model.put("surcharge", surcharge);

        return new ModelAndView("shop/frontend/confirmation");
    }

    public static class LoginForm {

        private String email;
        private String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

}
