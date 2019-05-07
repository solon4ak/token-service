package ru.tokens.site.registration;

import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import ru.tokens.site.entities.User;
import ru.tokens.site.services.UserService;
import ru.tokens.site.services.EmailSender;

/**
 *
 * @author solon4ak
 */
@Component
public class RegistrationListener 
        implements ApplicationListener<OnRegistrationCompleteEvent> {    
    
    private static final Logger log = LogManager.getLogger();
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private EmailSender sender;

    @Override
    public void onApplicationEvent(final OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(final OnRegistrationCompleteEvent event) {
        final User user = event.getUser();
        log.info("Insight listener. User: {}", user.toString());
        final String token = UUID.randomUUID().toString();
        userService.createActivationLink(user, token);
        
        sender.sendVerificationEmail(event, user, token);
    }
    
}
