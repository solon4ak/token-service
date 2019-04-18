package ru.tokens.site.utils;

import org.springframework.web.multipart.MultipartFile;
import ru.tokens.site.entities.Token;

/**
 *
 * @author solon4ak
 */
public interface FileUtil {

    public String getTomcatHomePath();

    public String getUSER_IMAGES();

    public void setUSER_IMAGES(String USER_IMAGES);

    public String getTOMCAT_HOME_PROPERTY();
    
    public void setTOMCAT_HOME_PROPERTY(String TOMCAT_HOME_PROPERTY);

    public String getNewFileNameBase();

    public String getNewFileName(MultipartFile file);

    public String getStorageDirectory(Token token);

}
