package ru.tokens.site.controller.admin.shop;

import java.awt.image.BufferedImage;
import java.awt.image.ImagingOpException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.tokens.site.entities.Image;
import ru.tokens.site.entities.shop.Category;
import ru.tokens.site.entities.shop.Product;
import ru.tokens.site.services.FileUtil;
import ru.tokens.site.services.ImageService;
import ru.tokens.site.services.shop.CategoryService;
import ru.tokens.site.services.shop.ProductService;

/**
 *
 * @author solon4ak
 */
@Controller
@RequestMapping("admin/shop")
public class ProductController {

    private static final Logger log = LogManager.getLogger("ProductController");

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    @Qualifier("fileService")
    private FileUtil fileUtil;
    
    @Autowired
    private ImageService imageService;

    // addProduct
    // viewProduct
    // editProduct
    // deleteProduct
    
    @RequestMapping(value = "product/{productId}/view", method = RequestMethod.GET)
    public String viewProduct(Map<String, Object> model, 
            @PathVariable("productId") Long productId) {
        model.put("product", this.productService.find(productId));
        return "shop/product/view";
    }
    
    @RequestMapping(value = "product/add", method = RequestMethod.GET)
    public String addProduct(Map<String, Object> model) {
        model.put("categories", this.categoryService.getAll());
        model.put("productForm", new ProductForm());

        return "shop/product/add";
    }

    @RequestMapping(value = "product/add", method = RequestMethod.POST)
    public ModelAndView addProduct(Map<String, Object> model, ProductForm form) {
        final String productName = form.getProductName();
        final String description = form.getDescription();
        final BigDecimal price = new BigDecimal(form.getPrice());

        final String[] categories = form.getCategories();
        final List<Category> ctgs = new LinkedList<>();
        for (String cat : categories) {
            for (Category category : this.categoryService.getAll()) {
                if (cat.equals(category.getCategoryName())) {
                    ctgs.add(category);
                }
            }
        }

        final Product product = new Product(productName, price, description);   
        for (Category ctg : ctgs) {
            product.addCategory(ctg);
            ctg.addItem(product);
        }
        
        List<Image> images = null;
        
        if (!form.getImages().isEmpty()) {
            try {
                images = this.processImages(form);
            } catch (IOException ex) {
                log.error("Error while processing images", ex);
            }
        }
        
        product.setPictures(images);
        product.setLastUpdated(product.getCreatedDate());        
        this.productService.create(product);
        
        model.put("product", product);
        return new ModelAndView("shop/product/view");
    }
    
    @RequestMapping(value = "product/image/{imgId}/view", method = RequestMethod.GET)
    public void picture(HttpServletResponse response, @PathVariable("imgId") Long imgId) {

        Image image = this.imageService.getImageById(imgId);
        File imageFile = new File(image.getUrl());

        response.setContentType(image.getContentType());
        response.setContentLength(image.getSize().intValue());
        try (InputStream is = new FileInputStream(imageFile)) {
            IOUtils.copy(is, response.getOutputStream());
        } catch (IOException e) {
            log.error("Could not show picture", e);
        }
    }
    
    @RequestMapping(value = "product/image/{imgId}/thumbnail", method = RequestMethod.GET)
    public void thumbnail(HttpServletResponse response, @PathVariable("imgId") Long imgId) {

        Image image = this.imageService.getImageById(imgId);
        File imageFile = new File(image.getThumbnailUrl());

        response.setContentType(image.getContentType());
        response.setContentLength(image.getThumbnailSize().intValue());
        try (InputStream is = new FileInputStream(imageFile)) {
            IOUtils.copy(is, response.getOutputStream());
        } catch (IOException e) {
            log.error("Could not show image thumbnail", e);
        }
    }
    
    @RequestMapping(value = "product/image/{imgId}/icon", method = RequestMethod.GET)
    public void showIcon(HttpServletResponse response, @PathVariable("imgId") Long imgId) {

        Image image = this.imageService.getImageById(imgId);
        File icon = new File(image.getIconUrl());

        response.setContentType(image.getContentType());
        response.setContentLength(image.getIconSize().intValue());
        try (InputStream is = new FileInputStream(icon)) {
            IOUtils.copy(is, response.getOutputStream());
        } catch (IOException e) {
            log.error("Could not show image icon", e);
        }
    }

    private synchronized List<Image> processImages(ProductForm form)
            throws IOException {
        List<Image> images = new LinkedList<>();
        for (MultipartFile file : form.getImages()) {
            if (file != null && file.getSize() > 0) {
                String newFileName = fileUtil.getNewFileName(file);
                String storageDirectory = fileUtil.getShopImgStorageDir();
                String newFilenameBase = fileUtil.getNewFileNameBase();

                File newFile = new File(storageDirectory + newFileName);

                try {
                    file.transferTo(newFile);

                    BufferedImage thumbnail = Scalr.resize(ImageIO.read(newFile), 290);
                    String thumbnailFilename = newFilenameBase + "-thumbnail.png";
                    File thumbnailFile = new File(storageDirectory + thumbnailFilename);
                    ImageIO.write(thumbnail, "png", thumbnailFile);
                    
                    BufferedImage icon = Scalr.resize(ImageIO.read(newFile), 100);
                    String iconFilename = newFilenameBase + "-icon.png";
                    File iconFile = new File(storageDirectory + iconFilename);
                    ImageIO.write(icon, "png", iconFile);

                    Image image = new Image();

                    image.setName(file.getOriginalFilename());
                    image.setThumbnailFilename(thumbnailFilename);
                    image.setIconFileName(iconFilename);
                    image.setNewFilename(newFileName);
                    image.setContentType(file.getContentType());
                    image.setSize(file.getSize());
                    image.setThumbnailSize(thumbnailFile.length());
                    image.setIconSize(iconFile.length());

                    image.setUrl(newFile.getCanonicalPath());
                    image.setThumbnailUrl(thumbnailFile.getCanonicalPath());
                    image.setIconUrl(iconFile.getCanonicalPath());
                    image.setDeleteUrl(null);
                    images.add(image);
                    this.imageService.saveImage(image);
                } catch (ImagingOpException | IOException
                        | IllegalArgumentException | IllegalStateException e) {
                    log.error("Could not upload file " + file.getOriginalFilename(), e);
                }
            }
        }
        return images;
    }

    public static class ProductForm {

        private String productName;
        private String price;
        private String description;
        private List<MultipartFile> images;
        private String[] categories;

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

        public String[] getCategories() {
            return categories;
        }

        public void setCategories(String[] categories) {
            this.categories = categories;
        }

    }
}
