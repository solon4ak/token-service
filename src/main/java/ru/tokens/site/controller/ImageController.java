package ru.tokens.site.controller;

import java.awt.image.BufferedImage;
import java.awt.image.ImagingOpException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import ru.tokens.site.entities.Image;
import ru.tokens.site.entities.Token;
import ru.tokens.site.entities.User;
import ru.tokens.site.utils.FileUtil;

/**
 *
 * @author solon4ak
 */
@Controller
@RequestMapping("token/user/image")
public class ImageController {

    private static final Logger log = LogManager.getLogger(ImageController.class);
    
    @Autowired
    @Qualifier("fileService")
    private FileUtil fileUtil;

    private volatile long IMAGE_ID_SEQUENCE = 1;

    private synchronized long getNextImageId() {
        return this.IMAGE_ID_SEQUENCE++;
    }
    
    @RequestMapping(value = "view", method = RequestMethod.GET)
    public void picture(HttpSession session, HttpServletResponse response) {

        Long tokenId = (Long) session.getAttribute("tokenId");
        Token token = TokenRegistrationController.getTokenDatabase().get(tokenId);
        User user = token.getUser();

        Image image = user.getImage();

//        String path = fileUtil.getStorageDirectory();
//        File imageFile = new File(path
//                + File.separator
//                + image.getNewFilename());

        File imageFile = new File(image.getUrl());

        response.setContentType(image.getContentType());
        response.setContentLength(image.getSize().intValue());
        try(InputStream is = new FileInputStream(imageFile)) {
            IOUtils.copy(is, response.getOutputStream());
        } catch (IOException e) {
            log.error("Could not show picture", e);
        }
    }

    @RequestMapping(value = "thumbnail", method = RequestMethod.GET)
    public void thumbnail(HttpSession session, HttpServletResponse response) {
        Long tokenId = (Long) session.getAttribute("tokenId");
        Token token = TokenRegistrationController.getTokenDatabase().get(tokenId);
        User user = token.getUser();

        Image image = user.getImage();

//        String path = fileUtil.getStorageDirectory();
//        File imageFile = new File(path
//                + File.separator
//                + image.getThumbnailFilename());

        File imageFile = new File(image.getThumbnailUrl());

        response.setContentType(image.getContentType());
        response.setContentLength(image.getThumbnailSize().intValue());
        try(InputStream is = new FileInputStream(imageFile)) {
            IOUtils.copy(is, response.getOutputStream());            
        } catch (IOException e) {
            log.error("Could not show thumbnail", e);
        }
    }

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String getImgForm(Map<String, Object> model) {
        model.put("imgForm", new ImageForm());
        return "image/edit/add";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public View upload(HttpSession session, ImageForm form) {

        Long tokenId = (Long) session.getAttribute("tokenId");
        Map<Long, Token> tokens = TokenRegistrationController.getTokenDatabase();
        Token token = tokens.get(tokenId);
        User user = token.getUser();
        
        if(user.getImage() != null) {
            this.deleteImage(token);
        }
        
        MultipartFile file = form.getFile();
        if (file != null && file.getSize() > 0) {
            String newFileName = fileUtil.getNewFileName(file);
            String storageDirectory = fileUtil.getStorageDirectory();
            String newFilenameBase = fileUtil.getNewFileNameBase();

            File newFile = new File(storageDirectory + newFileName);

            try {
                file.transferTo(newFile);

                BufferedImage thumbnail = Scalr.resize(ImageIO.read(newFile), 290);
                String thumbnailFilename = newFilenameBase + "-thumbnail.png";
                File thumbnailFile = new File(storageDirectory + thumbnailFilename);
                ImageIO.write(thumbnail, "png", thumbnailFile);

                Image image = new Image();
                image.setId(this.getNextImageId());
                
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
            } catch (ImagingOpException | IOException 
                    | IllegalArgumentException | IllegalStateException e) {
                log.error("Could not upload file " + file.getOriginalFilename(), e);
            }
        }        
        return new RedirectView("/token/user/view", true, false);
    }

    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public View delete(HttpSession session) {
        Long tokenId = (Long) session.getAttribute("tokenId");
        Token token = TokenRegistrationController.getTokenDatabase().get(tokenId);        
        this.deleteImage(token);
        return new RedirectView("/token/user/view", true, false);
    }
    
    public void deleteImage(Token token) {
        User user = token.getUser();
        Image image = user.getImage();
//        String path = fileUtil.getStorageDirectory();
//        
//        File imageFile = new File(path
//                + File.separator
//                + image.getNewFilename());
        File imageFile = new File(image.getUrl());
        imageFile.delete();
        
//        File thumbnailFile = new File(path
//                + File.separator
//                + image.getThumbnailFilename());
        File thumbnailFile = new File(image.getThumbnailUrl());
        thumbnailFile.delete();        

//        File directory = new File(path);
//        directory.delete();

        user.setImage(null);
    }

    public static class ImageForm {

        MultipartFile file;

        public MultipartFile getFile() {
            return file;
        }

        public void setFile(MultipartFile file) {
            this.file = file;
        }

    }

}
