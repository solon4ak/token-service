package ru.tokens.site.controller;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import ru.tokens.site.entities.User;
import ru.tokens.site.utils.PasswordUtil;

/**
 *
 * @author solon4ak
 */
@Controller
@RequestMapping(value = "user")
public class UserRegistrationController {

    @Autowired
    @Qualifier(value = "passayPasswordUtil")
    private PasswordUtil passwordUtil;

    private static final Logger log = LogManager.getLogger("User");

    private volatile long USER_ID_SEQUENCE = 1;

    private synchronized long getNextUserId() {
        return this.USER_ID_SEQUENCE++;
    }

    private final Map<Long, User> userDatabase = new LinkedHashMap<>();

    public Map<Long, User> getUserDatabase() {
        return userDatabase;
    }

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String register(Map<String, Object> model) {
        UserRegistrationForm form = new UserRegistrationForm();
        String password = passwordUtil.generatePassword();
        form.setPassword(password);
        System.out.println("--- Passworg generated: " + password);
        model.put("userRegistrationFailed", false);
        model.put("userRegistrationForm", form);
        return "userreg/registration";
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public ModelAndView register(Map<String, Object> model, HttpSession session,
            HttpServletRequest request, UserRegistrationForm form) {

        String lastName = form.getLastName();
        String firstName = form.getFirstName();
        String midName = form.getMiddleName();
        String password = form.getPassword();
        String phoneNumber = form.getPhoneNumber();
        
        String email = form.getEmail();

        for (Map.Entry<Long, User> entry : userDatabase.entrySet()) {
//            Long id = entry.getKey();
            User user = entry.getValue();
            if (email.equals(user.getUserEmailAddress())) {
                String msg = "User with email: " + email + " has already registered.";
                model.put("message", msg);
                model.put("userRegistrationFailed", true);
                form.setEmail("");
                model.put("userRegistrationForm", form);
                return new ModelAndView("userreg/registration");
            }
        }

        String birthDate = form.getBirthDate();
        LocalDate bdate;
        try {
            bdate = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        } catch (DateTimeParseException e) {
            String msg = "Неправильный формат даты. "
                    + "Введите дату в следующем формате '30.03.1985'.";
            model.put("message", msg);
            return new ModelAndView(new RedirectView("/token/view/error", true, true, true));
        }

        User user = new User();
        user.setRegistered(Instant.now());
        user.setUserId(this.getNextUserId());
        user.setLastName(lastName);
        user.setFirstName(firstName);
        user.setMiddleName(midName);
        user.setUserEmailAddress(email);
        user.setPassword(password);
        user.setPhoneNumber(phoneNumber);
        // check date less than today date
        user.setBirthDate(bdate);

        this.userDatabase.put(user.getUserId(), user);

        log.info("User '{}' successfully registered.", user.getLastName() + ", " + user.getFirstName());
        session.setAttribute("userId", user.getUserId());
        request.changeSessionId();
        return new ModelAndView(
                new RedirectView("/user/view", true, true, true)
        );
    }
    
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public ModelAndView editUserData(Map<String, Object> model, HttpSession session){
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return new ModelAndView(new RedirectView("/login", true, false));
        }

        User user = this.userDatabase.get(userId);
        if (user == null) {
            log.error("User with id {} doesn't exist.", userId);
            String msg = "User doesn't exist.";
            model.put("message", msg);
            return new ModelAndView("userreg/error");
        }
        
        UserRegistrationForm form = new UserRegistrationForm();
        
        form.setFirstName(user.getFirstName());
        form.setMiddleName(user.getMiddleName());
        form.setLastName(user.getLastName());
        form.setPhoneNumber(user.getPhoneNumber());
        form.setBirthDate(user.getBirthDate() == null
                ? "" : user.getBirthDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        );
        form.setPassword(user.getPassword());
        
        model.put("userRegistrationForm", form);
        model.put("user", user);
        
        return new ModelAndView("userreg/edit");
    }
    
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public View editUserData(Map<String, Object> model, HttpSession session, 
            UserRegistrationForm form) {
        
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return new RedirectView("/login", true, false);
        }

        User user = this.userDatabase.get(userId);
        if (user == null) {
            log.error("User with id {} doesn't exist.", userId);            
            return new RedirectView("/user/register", true, false);
        }
        
        user.setFirstName(form.getFirstName());
        user.setMiddleName(form.getMiddleName());
        user.setLastName(form.getLastName());
        user.setPassword(form.getPassword());
        user.setPhoneNumber(form.getPhoneNumber());
        
        String birthDate = form.getBirthDate();
        LocalDate bdate;
        try {
            bdate = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        } catch (DateTimeParseException e) {
            String msg = "Неправильный формат даты. "
                    + "Введите дату в следующем формате '30.03.1985'.";
            model.put("message", msg);
            return new RedirectView("/userreg/error", true, true, true);
        }
        // check date less than today date
        user.setBirthDate(bdate);
        
        return new RedirectView("/user/view", true, false);
    }
    
    @RequestMapping(value = "view", method = RequestMethod.GET)
    public ModelAndView viewUserPage(Map<String, Object> model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return new ModelAndView(new RedirectView("/login", true, false));
        }

        User user = this.userDatabase.get(userId);
        if (user == null) {
            log.error("User with id {} doesn't exist.", userId);
            String msg = "User doesn't exist.";
            model.put("message", msg);
            return new ModelAndView("userreg/error");
        }

        model.put("user", user);
        return new ModelAndView("userreg/view");
    }

    public static class UserRegistrationForm {

        private String firstName;
        private String lastName;
        private String middleName;
        private String password;
        private String email;
        private String phoneNumber;
        private String birthDate;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getMiddleName() {
            return middleName;
        }

        public void setMiddleName(String middleName) {
            this.middleName = middleName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getBirthDate() {
            return birthDate;
        }

        public void setBirthDate(String birthDate) {
            this.birthDate = birthDate;
        }
    }    

}
