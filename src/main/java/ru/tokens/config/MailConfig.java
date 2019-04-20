package ru.tokens.config;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;


/**
 *
 * @author solon4ak
 */
@Configuration
@Import(PropertyPlaceholderConfig.class)
public class MailConfig {
    
    /*mail.smtp.host=smtp.gmail.com
mail.smtp.password=D3m0p4S5!
mail.smtp.user=test@gmail.com
mail.smtp.port=465
mail.smtp.auth=true
mail.smtp.starttls.enable=true
mail.smtp.socketFactory.class=javax.net.ssl.SSLSocketFactory
mail.debug=false*/
    
    @Value("${mail.smtp.host}")
    private String mailHost;
    @Value("${mail.smtp.password}")
    private String mailPassword;
    @Value("${mail.smtp.user}")
    private String mailUser;
    @Value("${mail.smtp.port}")
    private String mailPort;
    @Value("${mail.smtp.auth}")
    private String mailAuth;
    @Value("${mail.smtp.starttls.enable}")
    private String mailStarttls;
    @Value("${mail.smtp.socketFactory.class}")
    private String mailSocketFactory;
    @Value("${mail.debug}")
    private String mailDebug;

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setDefaultEncoding("UTF-8");
        mailSender.setHost(mailHost);
        mailSender.setPassword(mailPassword);
        mailSender.setUsername(mailUser);
        mailSender.setPort(new Integer(mailPort));
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", mailAuth);
        properties.put("mail.smtp.starttls.enable", mailStarttls);
        properties.put("mail.smtp.socketFactory.class", mailSocketFactory);
        properties.put("mail.debug", mailDebug);
        mailSender.setJavaMailProperties(properties);
        return mailSender;
    }
}
