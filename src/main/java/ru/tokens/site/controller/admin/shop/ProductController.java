package ru.tokens.site.controller.admin.shop;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import ru.tokens.site.entities.Image;
import ru.tokens.site.entities.shop.Category;
import ru.tokens.site.entities.shop.Product;
import ru.tokens.site.services.ImageService;
import ru.tokens.site.services.shop.CategoryService;
import ru.tokens.site.services.shop.ProductService;

/**
 *
 * @author solon4ak
 */
@Controller
@RequestMapping("admin/shop/product")
public class ProductController {

    private static final Logger log = LogManager.getLogger("ProductController");

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ImageService imageService;

    @RequestMapping(value = "{productId:\\d+}/view", method = RequestMethod.GET)
    public String viewProduct(final Map<String, Object> model,
            final @PathVariable("productId") Long productId) {
        model.put("product", this.productService.find(productId));
        return "shop/product/view";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String addProduct(final Map<String, Object> model) {
        model.put("categories", this.categoryService.getAll());
        model.put("productForm", new ProductForm());
        return "shop/product/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ModelAndView addProduct(final Map<String, Object> model,
            final ProductForm form) {
        final String productName = form.getProductName();
        final String description = form.getDescription();
        final BigDecimal price = new BigDecimal(form.getPrice());

        final Long[] categories = form.getCategories();
        final List<Category> ctgs = new LinkedList<>();
        for (Long cat : categories) {
            ctgs.add(this.categoryService.find(cat));
        }

        List<Image> images = null;
        final List<MultipartFile> pictures = form.getImages();
        if (!pictures.isEmpty()) {
            try {
                images = this.productService.processImages(pictures);
            } catch (IOException ex) {
                log.error("Error while processing images", ex);
            }
        }

        final Product product = new Product(productName, price, description, images, ctgs);
//        product.setCategories(ctgs);
        for (Category ctg : ctgs) {
            if (!ctg.getItems().contains(product)) {
                ctg.addItem(product);
            }
        }

//        product.setPictures(images);
//        product.setLastUpdated(product.getCreatedDate());
        this.productService.create(product);

        model.put("product", product);
        return new ModelAndView("shop/product/view");
    }

    @RequestMapping(value = "{productId:\\d+}/edit", method = RequestMethod.GET)
    public String editProduct(final Map<String, Object> model,
            final @PathVariable("productId") Long productId) {

        final Product product = this.productService.find(productId);
        final ProductForm form = new ProductForm();

        form.setProductName(product.getProductName());
        form.setDescription(product.getDescription());
        form.setPrice(product.getInitialPrice().toPlainString());

        final Collection<Category> categories = this.categoryService.getAll();
        final List<Category> checkedCategories = product.getCategories();
        final Long[] categoriesId = new Long[checkedCategories.size()];
        for (int i = 0; i < checkedCategories.size(); i++) {
            categoriesId[i] = checkedCategories.get(i).getCategoryId();
        }
        form.setCategories(categoriesId);

        model.put("productForm", form);
        model.put("categories", categories);
        model.put("product", product);

        return "shop/product/edit";
    }

    @RequestMapping(value = "{productId:\\d+}/edit", method = RequestMethod.POST)
    public String editProduct(final Map<String, Object> model, final ProductForm form,
            @PathVariable("productId") Long productId) {
        final Product product = this.productService.find(productId);

        product.setProductName(form.getProductName());
        product.setDescription(form.getDescription());
        product.setInitialPrice(new BigDecimal(form.getPrice()));

        final Long[] selectedCategoriesIds = form.getCategories();
        final List<Category> selectedCategories = new LinkedList<>();
        for (Long id : selectedCategoriesIds) {
            selectedCategories.add(this.categoryService.find(id));
        }

        // Remove product from all categories
        this.productService.deleteProductFromCategories(productId);
//        for (Category category : this.categoryService.getAll()) {
//            category.removeItem(product);
//        }

        List<Image> images = null;
        final List<MultipartFile> pictures = form.getImages();
        if (!pictures.isEmpty()) {
            try {
                images = this.productService.processImages(pictures);
            } catch (IOException ex) {
                log.error("Error while processing images", ex);
            }
        }    

        product.setCategories(selectedCategories);

        // Add product to selected categories
        for (Category category : product.getCategories()) {
            category.addItem(product);
        }

        for (Image image : images) {
            product.addImage(image);
        }

//        product.setLastUpdated(LocalDate.now());
        this.productService.create(product);

        model.put("product", product);
        return "shop/product/view";
    }

    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String listProducts(final Map<String, Object> model) {
        model.put("products", this.productService.getAll());
        return "shop/product/list";
    }

    @RequestMapping(value = "{productId:\\d+}/delete", method = RequestMethod.GET)
    public String deleteProduct(final Map<String, Object> model,
            final @PathVariable("productId") Long productId) {

        final String message = String.format("Product '%s' was successfully deleted.",
                this.productService.find(productId).getProductName());
        this.productService.deleteProductImages(productId);
        this.productService.deleteProductFromCategories(productId);
        // remove product from product repository
        this.productService.delete(productId);

        model.put("message", message);
        model.put("products", this.productService.getAll());
        return "shop/product/list";
    }

    @RequestMapping(value = "image/{imgId:\\d+}/view", method = RequestMethod.GET)
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

    @RequestMapping(value = "image/{imgId:\\d+}/thumbnail", method = RequestMethod.GET)
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

    @RequestMapping(value = "image/{imgId:\\d+}/icon", method = RequestMethod.GET)
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

    @RequestMapping(value = "{prodId:\\d+}/image/{imgId:\\d+}/delete", method = RequestMethod.GET)
    public View deleteImage(final @PathVariable("prodId") Long prodId,
            final @PathVariable("imgId") Long imgId) {
        Product product = this.productService.find(prodId);
        this.productService.deleteImg(product, imgId);
        return new RedirectView("/admin/shop/product/" + prodId + "/edit", true, false);
    }

    public static class ProductForm {

        private String productName;
        private String price;
        private String description;
        private List<MultipartFile> images;
        private Long[] categories;

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<MultipartFile> getImages() {
            return images;
        }

        public void setImages(List<MultipartFile> images) {
            this.images = images;
        }

        public Long[] getCategories() {
            return categories;
        }

        public void setCategories(Long[] categories) {
            this.categories = categories;
        }

    }
}
