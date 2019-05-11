package ru.tokens.site.services;

import java.security.Principal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tokens.site.entities.UserPrincipal;

/**
 *
 * @author solon4ak
 */
@Service
public class TemporaryAuthenticationService implements AuthenticationService {
    
    private static final Logger log = LogManager.getLogger("AuthenticationService");

    @Autowired
    private UserService userService;
    
    @Override
    public Principal authenticate(String email, String password) {
        final String currentPassword = this.userService.getPasswordForUser(email);
        if (currentPassword == null) {
            log.warn("Authentication failed for non-existent user {}.", email);
            return null;
        }
        
        if (!currentPassword.equals(password)) {
            log.warn("Authentication failed for user {}.", email);
            return null;
        }
        
        log.debug("User {} successfully authenticated.", email);

        return new UserPrincipal(this.userService.findUserByEmail(email));
    }
    
}
