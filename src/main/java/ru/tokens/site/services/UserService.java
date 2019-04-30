package ru.tokens.site.services;

import ru.tokens.site.entities.ActivationLink;
import ru.tokens.site.entities.User;

/**
 *
 * @author solon4ak
 */
public interface UserService {
    
    void createActivationLink(final User user, final String token);
    
    ActivationLink getActivationLink(final String activationToken);
    
    String validateVerificationToken(final String activationToken);
    
    User getUser(final String activationToken);
    
    void deleteUser(User user);
}
