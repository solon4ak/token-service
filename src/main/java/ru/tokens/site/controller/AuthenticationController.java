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
import org.springframework.beans.factory.annotation.Autowired;
import ru.tokens.site.entities.User;

@Controller
public class AuthenticationController {
    
    @Autowired
    private UserRegistrationController userRegistrationController;

    private static final Logger log = LogManager.getLogger("AuthenticationController");

    private Map<Long, User> getUserDatabase() {
        return userRegistrationController.getUserDatabase();
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

        if (session.getAttribute("userId") != null) {
            return this.getUserRedirect();
        }

        model.put("loginFailed", false);
        model.put("loginForm", new Form());

        return new ModelAndView("login");
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ModelAndView login(Map<String, Object> model, HttpSession session,
            HttpServletRequest request, Form form) {

        if (session.getAttribute("userId") != null) {
            return this.getUserRedirect();
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

        Map<Long, User> users = this.getUserDatabase();

        for (User user : users.values()) {            
            if (email.equals(user.getUserEmailAddress())
                    && password.equals(user.getPassword()) 
                    && user.isEmailActivated()) {
                log.info("User {} successfully logged in.", email);
                session.setAttribute("userId", user.getUserId());
                request.changeSessionId();
                return this.getUserRedirect();
            }
        }

        log.info("User credentials doesn't correspond or user e-mail doesn't confirmed.");
        form.setPassword(null);
        model.put("loginFailed", true);
        model.put("loginForm", form);
        return new ModelAndView("login");
    }

    private ModelAndView getUserRedirect() {
        return new ModelAndView(
                new RedirectView("/user/view", true, false)
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
