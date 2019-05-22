package ru.tokens.site.controller.admin;

import java.security.Principal;
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
import ru.tokens.site.entities.UserPrincipal;
import ru.tokens.site.services.AuthenticationService;

/**
 *
 * @author solon4ak
 */
@Controller
public class AdminController {

    private static final Logger log = LogManager.getLogger("Admin");
    
    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(value = "admlogin", method = RequestMethod.GET)
    public ModelAndView login(Map<String, Object> model, HttpSession session) {

        if (UserPrincipal.getPrincipal(session) != null) {
            return this.getUserRedirect();
        }

        model.put("loginFailed", false);
        model.put("adminLoginForm", new AdminLoginForm());

        return new ModelAndView("admin/login");
    }

    @RequestMapping(value = "admlogin", method = RequestMethod.POST)
    public ModelAndView login(Map<String, Object> model, HttpSession session,
            HttpServletRequest request, AdminLoginForm form) {

        if (UserPrincipal.getPrincipal(session) != null) {
            return this.getUserRedirect();
        }
        
        Principal principal = this.authenticationService
                .authenticate(form.getEmail(), form.getPassword());

        if (principal == null) {
            log.warn("Principal == null");
            form.setPassword(null);
            model.put("loginFailed", true);
            model.put("adminLoginForm", form);
            return new ModelAndView("admin/login");
        }

        UserPrincipal.setPrincipal(session, principal);
        request.changeSessionId();
        return this.getUserRedirect();
    }
    
    @RequestMapping(value = "admpanel", method = RequestMethod.GET)
    public ModelAndView getAdminPanel(Map<String, Object> model, HttpSession session) {
        
        if (UserPrincipal.getPrincipal(session) == null) {
            return new ModelAndView(
                    new RedirectView("/admlogin", true, false));
        }
        
        return new ModelAndView("admin/panel");
    }
    
    private ModelAndView getUserRedirect() {
        return new ModelAndView(
                new RedirectView("/admpanel", true, false)
        );
    }

    public static class AdminLoginForm {
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
