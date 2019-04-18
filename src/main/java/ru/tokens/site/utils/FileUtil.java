package ru.tokens.site.utils;

import org.springframework.web.multipart.MultipartFile;
import ru.tokens.site.entities.Token;

/**
 *
 * @author solon4ak
 */
public interface FileUtil {

    public String getTomcatHomePath();

    public String getUserImages();

    public void setUserImages(String USER_IMAGES);

    public String getServerHomeProperty();
    
    public void setServerHomeProperty(String TOMCAT_HOME_PROPERTY);

    public String getNewFileNameBase();

    public String getNewFileName(MultipartFile file);

    public String getStorageDirectory(Token token);

}
