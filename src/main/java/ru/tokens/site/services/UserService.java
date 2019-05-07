package ru.tokens.site.services;

import java.util.List;
import ru.tokens.site.entities.ActivationLink;
import ru.tokens.site.entities.Token;
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
    
    void deleteUser(long id);
    
    User findUserByEmail(final String email);    
    
    User findUserById(final long id);
    
    User findByToken(Token token);
    
    void saveUser(final User user);
    
    List<User> getAllUsers();
    
    List<User> getAllUsersWithToken();
    
}
