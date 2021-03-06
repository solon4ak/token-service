package ru.tokens.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import ru.tokens.site.services.FileUtil;

/**
 *
 * @author solon4ak
 */
@Configuration
@Import(PropertyPlaceholderConfig.class)
@PropertySource("classpath:app.properties")
public class FileUploadConfig {
    
    @Value("${file.upload.directory}")
    private String userFilesDir;
    
    @Value("${file.upload.server.home}")
    private String serverHomeProp;
    
    @Value("${file.upload.shop.images.directory}")
    private String shopImagesDir;
    
    @Autowired
    @Qualifier("fileService")
    private FileUtil fileUtil;
    
    @Bean
    protected FileUtil fileProperties() {
        fileUtil.setUserDir(userFilesDir);
        fileUtil.setServerHomeProperty(serverHomeProp);
        fileUtil.setShopImagesDir(shopImagesDir);
        return fileUtil;
    }
}
