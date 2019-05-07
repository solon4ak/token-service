package ru.tokens.site.services;

import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author solon4ak
 */
public interface FileUtil {

    public String getTomcatHomePath();

    public String getUserDir();

    public void setUserDir(String userDir);

    public String getServerHomeProperty();
    
    public void setServerHomeProperty(String tomcatDir);

    public String getNewFileNameBase();

    public String getNewFileName(MultipartFile file);

    public String getStorageDirectory();

}
