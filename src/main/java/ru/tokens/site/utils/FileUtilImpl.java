package ru.tokens.site.utils;

import java.io.File;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.tokens.site.entities.Token;

/**
 *
 * @author solon4ak
 */
@Component(value = "fileService")
public class FileUtilImpl implements FileUtil {
        
    private String userImages;
    private String serverHomeProperty;
    
    @Override
    public String getTomcatHomePath() {
        return System.getProperty(serverHomeProperty);
    }

    @Override
    public String getUserImages() {
        return userImages;
    }

    @Override
    public void setUserImages(String userImages) {
        this.userImages = userImages;
    }

    @Override
    public String getServerHomeProperty() {
        return serverHomeProperty;
    }

    @Override
    public void setServerHomeProperty(String serverHomeProperty) {
        this.serverHomeProperty = serverHomeProperty;
    }    
    
    @Override
    public String getNewFileNameBase() {
        return UUID.randomUUID().toString();
    }

    @Override
    public String getNewFileName(MultipartFile file) {        
        String originalFileExtension = file
                .getOriginalFilename()
                .substring(file.getOriginalFilename().lastIndexOf("."));
        String newFileName = getNewFileNameBase() + originalFileExtension;

        return newFileName;
    }

    @Override
    public String getStorageDirectory(Token token) {
        String USER_IMAGES_PATH
                = this.getTomcatHomePath()
                + File.separator
                + userImages
                + File.separator
                + token.getUuidString();
        
        File USER_IMAGES_DIR = new File(USER_IMAGES_PATH);

        String USER_IMAGES_DIR_ABSOLUTE_PATH
                = USER_IMAGES_DIR.getAbsolutePath() + File.separator;

        if (!USER_IMAGES_DIR.exists()) {
            USER_IMAGES_DIR.mkdirs();
        }

        return USER_IMAGES_DIR_ABSOLUTE_PATH;
    }
}
