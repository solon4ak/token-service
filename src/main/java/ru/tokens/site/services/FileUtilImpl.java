package ru.tokens.site.services;

import java.io.File;
import java.time.LocalDate;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author solon4ak
 */
@Component(value = "fileService")
public class FileUtilImpl implements FileUtil {

    private String userDir;
    private String serverHomeProperty;

    @Override
    public String getTomcatHomePath() {
        return System.getProperty(serverHomeProperty);
    }

    @Override
    public String getUserDir() {
        return userDir;
    }

    @Override
    public void setUserDir(String userDir) {
        this.userDir = userDir;
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
    public synchronized String getStorageDirectory() {

        String userFilesPath
                = this.getTomcatHomePath()
                + File.separator
                + userDir
                + File.separator
                + this.getFileDir();

        File userImagesDir = new File(userFilesPath);

        String userImgAbsolutePath
                = userImagesDir.getAbsolutePath() + File.separator;

        if (!userImagesDir.exists()) {
            System.out.println("---> User dir does not exist!");
            userImagesDir.mkdirs();
        }
        System.out.println("UserImageAbsolutePath: " + userImgAbsolutePath);

        return userImgAbsolutePath;
    }

    public String getFileDir() {
        LocalDate date = LocalDate.now();
        String fileDir = date.getYear()
                + File.separator
                + date.getDayOfYear();        

        return fileDir;
    }
    
    
}
