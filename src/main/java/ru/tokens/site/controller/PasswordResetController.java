package ru.tokens.site.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ru.tokens.site.entities.User;
import ru.tokens.site.utils.EmailSender;
import ru.tokens.site.utils.PasswordUtil;

/**
 *
 * @author solon4ak
 */
@Controller
@RequestMapping("user")
public class PasswordResetController {
    
    @Autowired
    private UserRegistrationController userRegistrationController;
        
    @Autowired
    private EmailSender emailSender;
    
    @Autowired
    @Qualifier("defaultPasswordUtil")
    private PasswordUtil passwordUtil;
    
    @RequestMapping(value = "passwordreset", method = RequestMethod.GET)
    public String getResetPasswordForm(Map<String, Object> model) {
        model.put("passwordResetForm", new PasswordResetForm());
        model.put("wrongEmail", false);
        return "userreg/passwordreset";
    }
    
    @RequestMapping(value = "passwordreset", method = RequestMethod.POST)
    public ModelAndView resetPassword(Map<String, Object> model, 
            PasswordResetForm form) {
        String email = form.getEmail();
        User user = null;
        
        Map<Long, User> userDatabase = userRegistrationController.getUserDatabase();
        
        for (Map.Entry<Long, User> entry : userDatabase.entrySet()) {
            User aUser = entry.getValue();
            if (email.equals(aUser.getUserEmailAddress())) {
                user = aUser;
            }
        }
        
        if (null == user) {
            String msg = "Пользователь с данным адресом электронной почты не существует.";
            form.setEmail("");
            model.put("wrongEmail", true);
            model.put("message", msg);
            model.put("passwordResetForm", form);
            return new ModelAndView("userreg/passwordreset");
        }
        
        String newPassword = passwordUtil.generatePassword();
        user.setPassword(newPassword);
        emailSender.sendNewPasswordEmail(email, newPassword);
        System.out.println("---- New password for User " + user.getUserId() + " : " + newPassword);
        
        return new ModelAndView(new RedirectView("/login", true, false));
    }
    
    /* Model Attribute */    
    public static class PasswordResetForm {
        private String email;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        
    }
}
