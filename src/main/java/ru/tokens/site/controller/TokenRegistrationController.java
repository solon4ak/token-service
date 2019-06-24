package ru.tokens.site.controller;

import java.security.Principal;
import java.time.Instant;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ru.tokens.site.entities.Token;
import ru.tokens.site.entities.User;
import ru.tokens.site.services.TokenService;
import ru.tokens.site.services.UserService;
import ru.tokens.site.services.EmailSender;

/**
 *
 * @author solon4ak
 */
@Controller
@RequestMapping("token")
public class TokenRegistrationController {
    
    @Autowired
    private UserService userService;    
    
    @Autowired
    private EmailSender emailSender;
    
    @Autowired
    private TokenService tokenService;
    
    private static final Logger log = LogManager.getLogger("Token");
    
    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String tokenRegisterForm(final Map<String, Object> model) {
        model.put("registrationFailed", false);
        model.put("tokenRegistrationForm", new TokenRegistrationForm());
        return "token/view/registration";
    }
    
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public ModelAndView tokenFormSubmit(final Map<String, Object> model, 
            final Principal principal, final HttpServletRequest request, 
            final TokenRegistrationForm form) {
        
        final Long userId = Long.valueOf(principal.getName());        
        final User user = userService.findUserById(userId);

        // TODO валидация полей
        String msg;
        String uuidString = form.getUuid();
        String activationCode = form.getActivationCode();
        
        if (uuidString.isEmpty() || uuidString.trim().length() < 36
                || activationCode.isEmpty() || activationCode.trim().length() < 12) {
            msg = "Id жетона или код активации не совпадают.";
            model.put("message", msg);
            model.put("uuidString", uuidString);
            return new ModelAndView("token/view/registration");
        }
        
        final Token token = this.tokenService.findTokenByUUIDString(uuidString);
        
        if (token == null) {
            msg = "Жетон с таким номером не существует.";
            model.put("message", msg);
            model.put("uuidString", uuidString);
            return new ModelAndView("token/view/registration");
        }
        
        if (token.isActivated()) {
            msg = "Жетон с данным номером уже был активирован.";
            model.put("message", msg);
            model.put("uuidString", uuidString);
            return new ModelAndView("token/view/registration");
        }
        
        if (!(token.getActivationCode()).equals(activationCode)) {
            log.warn("Token credentials incorrect for userId '{}'.", uuidString);
            form.setActivationCode(null);
            model.put("registrationFailed", true);
            model.put("tokenRegistrationForm", new TokenRegistrationForm());
            return new ModelAndView("token/view/registration");
        }
        
        log.info("Token '{}' successfully entered.", uuidString);
        user.setToken(token);
        token.setUser(user);
        token.setActivated(true);
        token.setActivatedDate(Instant.now());
        
//        Confirmation e-mail
        String subject = "tag4life: token registration";
        String body = "Token - " + token.getUuidString() + "\r\n"
                + "successfully registered for user - " 
                + user.toString() + ".";
        emailSender.sendSimpleEmail(user.getUserEmailAddress(), subject, body);
        return new ModelAndView(
                new RedirectView("/token/user/view", true, true, true)
        );
    }
    
    public static class TokenRegistrationForm {
        
        private String uuid;
        private String activationCode;
        
        public String getUuid() {
            return uuid;
        }
        
        public String getActivationCode() {
            return activationCode;
        }
        
        public void setUuid(String uuid) {
            this.uuid = uuid;
        }
        
        public void setActivationCode(String activationCode) {
            this.activationCode = activationCode;
        }
    }
}
