package ru.tokens.site.controller;

import java.awt.image.BufferedImage;
import java.awt.image.ImagingOpException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import ru.tokens.site.entities.Image;
import ru.tokens.site.entities.Token;
import ru.tokens.site.entities.User;
import ru.tokens.site.services.TokenService;
import ru.tokens.site.services.UserService;
import ru.tokens.site.services.FileUtil;
import ru.tokens.site.services.ImageService;

/**
 *
 * @author solon4ak
 */
@Controller
@RequestMapping("token")
public class ImageController {

    private static final Logger log = LogManager.getLogger("ImageController");

    @Autowired
    @Qualifier("fileService")
    private FileUtil fileUtil;

    @Autowired
    private UserService userService;
    
    @Autowired
    private TokenService tokenService;
    
    @Autowired
    private ImageService imageService;

    @RequestMapping(value = "user/image/view", method = RequestMethod.GET)
    public void picture(final Principal principal, final HttpServletResponse response) {

        final Long userId = Long.valueOf(principal.getName());         
        final User user = userService.findUserById(userId);
        final Image image = user.getImage();
        final File imageFile = new File(image.getUrl());

        response.setContentType(image.getContentType());
        response.setContentLength(image.getSize().intValue());
        try (InputStream is = new FileInputStream(imageFile)) {
            IOUtils.copy(is, response.getOutputStream());
        } catch (IOException e) {
            log.error("Could not show picture", e);
        }
    }
    
    @RequestMapping(value = "image", method = RequestMethod.GET)
    public void picture(final HttpServletResponse response, 
            final @RequestParam("userId") Long userId) {

        final User user = userService.findUserById(userId);
        final Image image = user.getImage();
        final File imageFile = new File(image.getUrl());

        response.setContentType(image.getContentType());
        response.setContentLength(image.getSize().intValue());
        try (InputStream is = new FileInputStream(imageFile)) {
            IOUtils.copy(is, response.getOutputStream());
        } catch (IOException e) {
            log.error("Could not show picture", e);
        }
    }

    @RequestMapping(value = "user/image/thumbnail", method = RequestMethod.GET)
    public void thumbnail(final Principal principal, final HttpServletResponse response) {

        final Long userId = Long.valueOf(principal.getName());
        final User user = userService.findUserById(userId);
        final Image image = user.getImage();
        final File imageFile = new File(image.getThumbnailUrl());

        response.setContentType(image.getContentType());
        response.setContentLength(image.getThumbnailSize().intValue());
        try (InputStream is = new FileInputStream(imageFile)) {
            IOUtils.copy(is, response.getOutputStream());
        } catch (IOException e) {
            log.error("Could not show thumbnail", e);
        }
    }
    
    @RequestMapping(value = "thumb", method = RequestMethod.GET)
    public void thumbnail(final HttpServletResponse response,
            final @RequestParam("userId") Long userId) {
        
        final User user = userService.findUserById(userId);
        final Image image = user.getImage();
        final File imageFile = new File(image.getThumbnailUrl());

        response.setContentType(image.getContentType());
        response.setContentLength(image.getThumbnailSize().intValue());
        try (InputStream is = new FileInputStream(imageFile)) {
            IOUtils.copy(is, response.getOutputStream());
        } catch (IOException e) {
            log.error("Could not show thumbnail", e);
        }
    }

    @RequestMapping(value = "user/image/upload", method = RequestMethod.GET)
    public ModelAndView getImgForm(final Map<String, Object> model, 
            final Principal principal) {

        final Long userId = Long.valueOf(principal.getName());
        final User user = userService.findUserById(userId);

        final Token token = tokenService.findTokenByUser(user);
        if (token == null || !token.isActivated()) {
            return new ModelAndView(new RedirectView("/token/register", true, false));
        }

        model.put("token", token);
        model.put("user", user);
        model.put("imgForm", new ImageForm());
        return new ModelAndView("image/edit/add");
    }

    @RequestMapping(value = "user/image/upload", method = RequestMethod.POST)
    public View upload(final Principal principal, final ImageForm form) {

        final Long userId = Long.valueOf(principal.getName());
        final User user = userService.findUserById(userId);

        if (user.getImage() != null) {
            this.deleteImage(user);
        }

        final MultipartFile file = form.getFile();
        if (file != null && file.getSize() > 0) {
            final String newFileName = fileUtil.getNewFileName(file);
            final String storageDirectory = fileUtil.getStorageDirectory();
            final String newFilenameBase = fileUtil.getNewFileNameBase();

            final File newFile = new File(storageDirectory + newFileName);

            try {
                file.transferTo(newFile);

                final BufferedImage thumbnail = Scalr.resize(ImageIO.read(newFile), 290);
                final String thumbnailFilename = newFilenameBase + "-thumbnail.png";
                final File thumbnailFile = new File(storageDirectory + thumbnailFilename);
                ImageIO.write(thumbnail, "png", thumbnailFile);

                final Image image = new Image();

                image.setName(file.getOriginalFilename());
                image.setThumbnailFilename(thumbnailFilename);
                image.setNewFilename(newFileName);
                image.setContentType(file.getContentType());
                image.setSize(file.getSize());
                image.setThumbnailSize(thumbnailFile.length());

                image.setUrl(newFile.getCanonicalPath());
                image.setThumbnailUrl(thumbnailFile.getCanonicalPath());
                image.setDeleteUrl(null);
                user.setImage(image);
                imageService.saveImage(image);
            } catch (ImagingOpException | IOException
                    | IllegalArgumentException | IllegalStateException e) {
                log.error("Could not upload file " + file.getOriginalFilename(), e);
            }
        }
        return new RedirectView("/token/user/view", true, false);
    }

    @RequestMapping(value = "user/image/delete", method = RequestMethod.GET)
    public View delete(final Principal principal) {

        final Long userId = Long.valueOf(principal.getName());
        final User user = userService.findUserById(userId);

        this.deleteImage(user);
        return new RedirectView("/token/user/view", true, false);
    }

    public synchronized void deleteImage(final User user) {
        
        final Image image = user.getImage();

        final File imageFile = new File(image.getUrl());
        imageFile.delete();

        final File thumbnailFile = new File(image.getThumbnailUrl());
        thumbnailFile.delete();

        user.setImage(null);
        this.imageService.deleteImage(image.getId());
    }

    public static class ImageForm {

        private MultipartFile file;

        public MultipartFile getFile() {
            return file;
        }

        public void setFile(MultipartFile file) {
            this.file = file;
        }

    }

}
