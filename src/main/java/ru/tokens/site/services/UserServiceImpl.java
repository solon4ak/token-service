package ru.tokens.site.services;

import java.util.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tokens.site.entities.ActivationLink;
import ru.tokens.site.entities.User;
import ru.tokens.site.repository.ActivationLinkRepository;

/**
 *
 * @author solon4ak
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private ActivationLinkRepository activationLinkRepository;
    
    public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valid";

    @Override
    public void createActivationLink(final User user, final String activationUUIDString) {
        final ActivationLink activationLink = new ActivationLink(user, activationUUIDString);
        activationLinkRepository.save(activationLink);
    }

    @Override
    public ActivationLink getActivationLink(final String activationToken) {
        return activationLinkRepository.findByToken(activationToken);
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
    
    }

}
