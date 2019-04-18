package ru.tokens.site.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import ru.tokens.site.entities.Token;
import ru.tokens.site.entities.User;

@Controller
public class AuthenticationController {

    private static final Logger log = LogManager.getLogger("AuthenticationController");

    private Map<Long, Token> getTokenDatabase() {
        return TokenRegistrationController.getTokenDatabase();
    }

    @RequestMapping("logout")
    public View logout(HttpSession session) {
        if (log.isDebugEnabled()) {
            log.info("Token {} logged out.", session.getAttribute("tokenId"));
        }
        session.invalidate();
        return new RedirectView("/login", true, false);
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public ModelAndView login(Map<String, Object> model, HttpSession session) {

        if (session.getAttribute("tokenId") != null) {
            return this.getTokenRedirect();
        }

        model.put("loginFailed", false);
        model.put("loginForm", new Form());

        return new ModelAndView("login");
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ModelAndView login(Map<String, Object> model, HttpSession session,
            HttpServletRequest request, Form form) {

        if (session.getAttribute("tokenId") != null) {
            return this.getTokenRedirect();
        }

        String email = form.getEmail();
        String password = form.getPassword();

        if (email == null || password == null) {
            log.warn("Empty email or password field.");
            form.setPassword(null);
            model.put("loginFailed", true);
            model.put("loginForm", form);
            return new ModelAndView("login");
        }

        Map<Long, Token> tokens = this.getTokenDatabase();
        
        User user = null;
        for (Token tkn : tokens.values()) {
            user = tkn.getUser();
            if (user != null && email.equals(user.getEmail())
                    && password.equals(user.getPassword())) {
                log.info("User {} successfully logged in.", email);
                session.setAttribute("tokenId", tkn.getTokenId());
                request.changeSessionId();
                return this.getTokenRedirect();
            }
        }

        log.info("User credentials do not correspond.");
        form.setPassword(null);
        model.put("loginFailed", true);
        model.put("loginForm", form);
        return new ModelAndView("login");
    }

    private ModelAndView getTokenRedirect() {
        return new ModelAndView(
                new RedirectView("/token/user/view", true, false)
        );
    }

    public static class Form {

        private String email;
        private String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
