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
        
    private String USER_IMAGES;
    private String TOMCAT_HOME_PROPERTY;
    
    @Override
    public String getTomcatHomePath() {
        return System.getProperty(TOMCAT_HOME_PROPERTY);
    }

    @Override
    public String getUSER_IMAGES() {
        return USER_IMAGES;
    }

    @Override
    public void setUSER_IMAGES(String USER_IMAGES) {
        this.USER_IMAGES = USER_IMAGES;
    }

    @Override
    public String getTOMCAT_HOME_PROPERTY() {
        return TOMCAT_HOME_PROPERTY;
    }

    @Override
    public void setTOMCAT_HOME_PROPERTY(String TOMCAT_HOME_PROPERTY) {
        this.TOMCAT_HOME_PROPERTY = TOMCAT_HOME_PROPERTY;
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
                + USER_IMAGES
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
