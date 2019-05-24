package ru.tokens.site.services.shop;

import java.awt.image.BufferedImage;
import java.awt.image.ImagingOpException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.tokens.site.entities.Image;
import ru.tokens.site.entities.shop.Category;
import ru.tokens.site.entities.shop.Product;
import ru.tokens.site.repository.shop.ProductRepository;
import ru.tokens.site.services.FileUtil;
import ru.tokens.site.services.ImageService;

/**
 *
 * @author solon4ak
 */
@Service
public class ProductServiceDefImpl implements ProductService {

    private static final Logger log = LogManager.getLogger("ProductServiceDefImpl");

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    @Qualifier("fileService")
    private FileUtil fileUtil;

    @Override
    public List<Product> getAll() {
        return this.productRepository.list();
    }

    @Autowired
    private ImageService imageService;

    @Override
    public List<Product> getAllForCategory(Category category) {
        List<Product> generated = new ArrayList<>();
        for (Product product : this.getAll()) {
            if (product.getCategories().contains(category)) {
                generated.add(product);
            }
        }
        return generated;
    }

    @Override
    public Product find(long id) {
        return this.productRepository.find(id);
    }

    @Override
    public Product create(Product product) {
        LocalDate now = LocalDate.now();
        if (product.getProductId() < 1) {
            this.productRepository.create(product);
            product.setCreatedDate(now);
            product.setLastUpdated(now);
        } else {
            this.productRepository.update(product);
            product.setLastUpdated(now);
        }
        return product;
    }

    @Override
    public void delete(long id) {
        this.productRepository.delete(id);
    }

    @Override
    public synchronized List<Image> processImages(List<MultipartFile> pictures) throws IOException {
        List<Image> images = new LinkedList<>();
        for (MultipartFile file : pictures) {
            if (file != null && file.getSize() > 0) {
                String newFileName = fileUtil.getNewFileName(file);
                String storageDirectory = fileUtil.getShopImgStorageDir();
                String newFilenameBase = fileUtil.getShopImgFileDir();

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
                    image.setDeleteUrl(storageDirectory);
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

    @Override
    public synchronized void deleteImg(Product product, Long imageId) {
        Image image = this.imageService.getImageById(imageId);

        File imageFile = new File(image.getUrl());
        imageFile.delete();

        File thumbnailFile = new File(image.getThumbnailUrl());
        thumbnailFile.delete();

        File iconFile = new File(image.getIconUrl());
        iconFile.delete();

        File imgDir = new File(image.getDeleteUrl());
        imgDir.delete();

        product.getPictures().remove(image);
        this.imageService.deleteImage(image.getId());
    }

    @Override
    public synchronized void deleteProductImages(long productId) {

        final Product product = this.find(productId);

        // remove product images
        final Collection<Image> productImages = product.getPictures();
        for (Image image : productImages) {
//            this.deleteImg(product, img.getId());
            File imageFile = new File(image.getUrl());
            imageFile.delete();

            File thumbnailFile = new File(image.getThumbnailUrl());
            thumbnailFile.delete();

            File iconFile = new File(image.getIconUrl());
            iconFile.delete();

            File imgDir = new File(image.getDeleteUrl());
            imgDir.delete();

            this.imageService.deleteImage(image.getId());
        }

    }

    @Override
    public void deleteProductFromCategories(long productId) {
        // Remove product from all categories
        for (Category category : this.categoryService.getAll()) {
            category.removeItem(this.find(productId));
        }
    }

}
