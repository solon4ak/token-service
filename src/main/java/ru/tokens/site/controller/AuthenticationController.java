package ru.tokens.site.controller;

import java.security.Principal;
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
import ru.tokens.site.entities.UserPrincipal;
import ru.tokens.site.services.AuthenticationService;

@Controller
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    private static final Logger log = LogManager.getLogger("AuthenticationController");

    @RequestMapping("logout")
    public View logout(HttpServletRequest request, HttpSession session) {
        if (log.isDebugEnabled() && request.getUserPrincipal() != null) {
            log.debug("User {} logged out.", request.getUserPrincipal().getName());
        }
        session.invalidate();
        return new RedirectView("/login", true, false);
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public ModelAndView login(Map<String, Object> model, HttpSession session) {

        if (UserPrincipal.getPrincipal(session) != null) {
            return this.getUserRedirect();
        }

        model.put("loginFailed", false);
        model.put("loginForm", new Form());

        return new ModelAndView("login");
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ModelAndView login(Map<String, Object> model, HttpSession session,
            HttpServletRequest request, Form form) {

        if (UserPrincipal.getPrincipal(session) != null) {
            return this.getUserRedirect();
        }

        Principal principal = this.authenticationService
                .authenticate(form.getEmail(), form.getPassword());

        if (principal == null) {
            log.warn("Principal == null");
            form.setPassword(null);
            model.put("loginFailed", true);
            model.put("loginForm", form);
            return new ModelAndView("login");
        }

        UserPrincipal.setPrincipal(session, principal);
        request.changeSessionId();
        return this.getUserRedirect();
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
