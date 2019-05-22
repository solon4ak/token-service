package ru.tokens.site.services;

import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author solon4ak
 */
public interface FileUtil {

    String getTomcatHomePath();

    String getUserDir();

    void setUserDir(String userDir);
    
    String getShopImagesDir();
    
    void setShopImagesDir(String imgDir);

    String getServerHomeProperty();
    
    void setServerHomeProperty(String tomcatDir);

    String getNewFileNameBase();

    String getNewFileName(MultipartFile file);

    String getStorageDirectory();
    
    String getShopImgStorageDir();
    
    String getFileDir();
    
    String getShopImgFileDir();

}
