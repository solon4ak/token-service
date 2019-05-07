package ru.tokens.site.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tokens.site.entities.ActivationLink;
import ru.tokens.site.entities.Token;
import ru.tokens.site.entities.User;
import ru.tokens.site.repository.ActivationLinkRepository;
import ru.tokens.site.repository.UserRepository;

// import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author solon4ak
 */
@Service
public class UserServiceImpl implements UserService {
    
    private static final Logger log = LogManager.getLogger("User Service");
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivationLinkRepository activationLinkRepository;
    
    public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valid";

    @Override
    public void createActivationLink(final User user, final String activationUUIDString) {
        final ActivationLink activationLink = new ActivationLink(user, activationUUIDString);
        log.info("Activation link created for User: {}", user);
        activationLinkRepository.save(activationLink);
    }

    @Override
    public ActivationLink getActivationLink(final String activationToken) {
        ActivationLink link = activationLinkRepository.findByToken(activationToken);
        log.info("Activation link {} found for User: {}", link.getToken(), link.getUser().toString());
        return link;
    }

    @Override
    public String validateVerificationToken(String activationToken) {
        final ActivationLink activationLink = activationLinkRepository.findByToken(activationToken);
        if (activationLink == null) {
            return TOKEN_INVALID;
        }
        
        final User user = activationLink.getUser();
        final Calendar calendar = Calendar.getInstance();
        if ((activationLink.getExpiryDate().getTime() 
                - calendar.getTime().getTime()) 
                <= 0) {
            activationLinkRepository.delete(activationLink);
            return TOKEN_EXPIRED;
        }
        
        user.setEmailActivated(true);
        // activationLinkRepository.delete(activationLink);
        // userRepository.save(user);
        return TOKEN_VALID;
    }

    @Override
    public User getUser(String activationToken) {
        return this.getActivationLink(activationToken).getUser();
    }

    @Override
    public void deleteUser(User user) {
        this.userRepository.delete(user.getUserId());
    }
    
    @Override
    public void deleteUser(long id) {
        this.userRepository.delete(id);
    }

    @Override
    public User findUserByEmail(String email) {
        for (User user : this.userRepository.getAll()) {
            if (email.equals(user.getUserEmailAddress())) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User findUserById(long id) {
        return this.userRepository.get(id);
    }
    @Override
    public User findByToken(Token token) {
        for (User user : this.userRepository.getAll()) {
            if (token.equals(user.getToken())) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void saveUser(User user) {
        if(user.getUserId() < 1) {
            userRepository.add(user);
        } else {
            userRepository.update(user);
        }
    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepository.getAll();
    }
    
    @Override
    public List<User> getAllUsersWithToken() {
        List<User> usersWithToken = new ArrayList<>();
        for (User user : this.userRepository.getAll()) {
            if (user.getToken() != null) {
                usersWithToken.add(user);
            }
        }
        return usersWithToken;
    }    
 
}
