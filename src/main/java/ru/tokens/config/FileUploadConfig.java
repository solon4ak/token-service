package ru.tokens.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import ru.tokens.site.utils.FileUtil;

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
    
    @Autowired
    @Qualifier("fileService")
    private FileUtil fileUtil;
    
    @Bean
    protected FileUtil fileProperties() {
        fileUtil.setUserImages(userFilesDir);
        fileUtil.setServerHomeProperty(serverHomeProp);
        return fileUtil;
    }
}
