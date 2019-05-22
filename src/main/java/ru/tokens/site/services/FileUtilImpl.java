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

    private String shopImgDir;

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
    public String getShopImagesDir() {
        return shopImgDir;
    }

    @Override
    public void setShopImagesDir(String imgDir) {
        this.shopImgDir = imgDir;
    }

    @Override
    public synchronized String getNewFileNameBase() {
        return UUID.randomUUID().toString();
    }

    @Override
    public synchronized String getNewFileName(MultipartFile file) {
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
                + this.getUserDir()
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

    @Override
    public synchronized String getFileDir() {
        LocalDate date = LocalDate.now();
        String fileDir = date.getYear()
                + File.separator
                + date.getDayOfYear();

        return fileDir;
    }

    @Override
    public String getShopImgStorageDir() {
        String shopFilesPath
                = this.getTomcatHomePath()
                + File.separator
                + this.getShopImagesDir()
                + File.separator
                + this.getShopImgFileDir();

        File shopImagesDir = new File(shopFilesPath);

        String shopImgAbsolutePath
                = shopImagesDir.getAbsolutePath() + File.separator;

        if (!shopImagesDir.exists()) {
            System.out.println("---> Shop imgs dir does not exist!");
            shopImagesDir.mkdirs();
        }
        System.out.println("ShopImagesAbsolutePath: " + shopImgAbsolutePath);

        return shopImgAbsolutePath;
    }

    @Override
    public String getShopImgFileDir() {        
        return this.getNewFileNameBase();
    }

}
