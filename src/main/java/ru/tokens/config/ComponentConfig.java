package ru.tokens.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 *
 * @author solon4ak
 */
@Configuration
@ComponentScan(basePackages = {
    "ru.tokens.site"})
@PropertySource("classpath:app.properties")
public class ComponentConfig {
    
}
