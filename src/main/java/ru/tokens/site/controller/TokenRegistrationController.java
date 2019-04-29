package ru.tokens.site.controller;

import java.time.Instant;
import java.util.Hashtable;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
import ru.tokens.site.utils.TokenUtils;
import ru.tokens.site.utils.AppInitUtil;

/**
 *
 * @author solon4ak
 */
@Controller
@RequestMapping("token")
public class TokenRegistrationController {
    
    @Autowired
    private UserRegistrationController userRegistrationController;

    private static final Logger log = LogManager.getLogger("Token");

    private static final Map<Long, Token> tokenDatabase = new Hashtable<>();

    static {
        AppInitUtil util = new AppInitUtil();
        TokenUtils tokenUtils = util.getTokenUtils();
        Token t1 = tokenUtils.generateToken();

        tokenDatabase.put(t1.getTokenId(), t1);
        System.out.println("Token #1 id: " + t1.getTokenId());
        System.out.println("Token #1 uuidString: " + tokenDatabase.get(t1.getTokenId()).getUuidString());
        System.out.println("Token #1 Activation String: "
                + tokenDatabase.get(t1.getTokenId()).getActivationCode());

        Token t2 = tokenUtils.generateToken();

        tokenDatabase.put(t2.getTokenId(), t2);
        System.out.println("Token #2 id: " + t2.getTokenId());
        System.out.println("Token #2 uuidString: " + tokenDatabase.get(t2.getTokenId()).getUuidString());
        System.out.println("Token #2 Activation String: "
                + tokenDatabase.get(t2.getTokenId()).getActivationCode());
    }

    public static Map<Long, Token> getTokenDatabase() {
        return tokenDatabase;
    }

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String tokenRegisterForm(Map<String, Object> model) {
        model.put("registrationFailed", false);
        model.put("tokenRegistrationForm", new TokenRegistrationForm());
        return "token/view/registration";
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public ModelAndView tokenFormSubmit(Map<String, Object> model, HttpSession session,
            HttpServletRequest request, TokenRegistrationForm form) {
        
        Long userId = (Long) session.getAttribute("userId");        
        if (userId == null) {
            return new ModelAndView(new RedirectView("/login", true, false));
        }
        
        User user = userRegistrationController.getUserDatabase().get(userId);

        // TODO валидация полей
        String msg;
        String uuidString = form.getUuid();
        String activationCode = form.getActivationCode();

        if (uuidString.isEmpty() || uuidString.trim().length() < 36
                || activationCode.isEmpty() || activationCode.trim().length() < 12) {
            msg = "Id жетона или код активации не совпадают.";
            model.put("message", msg);
            model.put("uuidString", uuidString);
            return new ModelAndView("token/view/error");
        }

        Map<Long, Token> tokens = TokenRegistrationController.getTokenDatabase();
        Token token = null;

        for (Map.Entry<Long, Token> entry : tokens.entrySet()) {
            Token value = entry.getValue();
            if (uuidString.equals(value.getUuidString())) {
                token = value;
            }
        }

        if (token == null) {
            msg = "Жетон с таким номером не существует.";
            model.put("message", msg);
            model.put("uuidString", uuidString);
            return new ModelAndView("token/view/error");
        }

        if (token.isActivated()) {
            msg = "Жетон с данным номером уже был активирован.";
            model.put("message", msg);
            model.put("uuidString", uuidString);
            return new ModelAndView("token/view/error");
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
