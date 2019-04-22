package ru.tokens.site.controller;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import ru.tokens.site.entities.Token;
import ru.tokens.site.entities.User;
import ru.tokens.site.utils.TimeUtils;
import ru.tokens.site.utils.PasswordUtil;

/**
 *
 * @author solon4ak
 */
@Controller
@RequestMapping("token")
public class UserDataController {
    
    @Autowired
    @Qualifier(value = "passayPasswordUtil")
    private PasswordUtil passwordUtil;

    private static final Logger log = LogManager.getLogger("User");

    @RequestMapping(value = "{tokenId}/{uuidString}", method = RequestMethod.GET)
    public ModelAndView view(Map<String, Object> model, HttpSession session,
            @PathVariable("tokenId") Long tokenId,
            @PathVariable("uuidString") String uuidString,
            @RequestParam("showMH") boolean showMH) {

        Map<Long, Token> tokens = TokenRegistrationController.getTokenDatabase();
        String msg;

        if (tokenId == null || uuidString == null) {
            msg = "Такой жетон не существует";
            model.put("message", msg);
            model.put("uuidString", uuidString);
            return new ModelAndView("token/view/error");
        }

        Token token = tokens.get(tokenId);

        if (token == null || !token.isActivated()
                || !uuidString.equals(token.getUuidString())) {
            msg = "Жетон не существует или не активирован";
            model.put("message", msg);
            model.put("uuidString", uuidString);
            return new ModelAndView("token/view/error");
        }

        User user = token.getUser();
        LocalDate birthdate = user.getBirthDate();
        int age = TimeUtils.getYearsPassedFromDate(birthdate);

        model.put("token", token);
        model.put("age", age);

        System.out.println("--- goes to view page for outer context for tokenId " + tokenId);
        if (showMH) {
            return new ModelAndView("user/view/view");
        }
        return new ModelAndView("user/view/address");
    }

    @RequestMapping(value = "user/view", method = RequestMethod.GET)
    public ModelAndView view(Map<String, Object> model, HttpSession session) {
        Long tokenId = (Long) session.getAttribute("tokenId");

        Map<Long, Token> tokens = TokenRegistrationController.getTokenDatabase();

        Token token = tokens.get(tokenId);
        if (token == null) {
            return new ModelAndView("login");
        }

        model.put("token", token);
        System.out.println("--- goes to view page for registered user with tokenId " + tokenId);
        return new ModelAndView("user/edit/view");
    }

    @RequestMapping(value = {"add/user"}, method = RequestMethod.GET)
    public ModelAndView addUserForm(Map<String, Object> model, HttpSession session) {

        Long tokenId = (Long) session.getAttribute("tokenId");
        if (tokenId == null) {
            session.invalidate();
            return new ModelAndView(new RedirectView("/token/register", true, false));
        }

        Map<Long, Token> tokens = TokenRegistrationController.getTokenDatabase();
        Token token = tokens.get(tokenId);

        String password = passwordUtil.generatePassword();
        System.out.println("--- User password: " + password);

        UserForm userForm = new UserForm();
        userForm.setPassword(password);
        model.put("userForm", userForm);
        model.put("token", token);
        System.out.println("--- entering adding user form");
        return new ModelAndView("user/edit/add");
    }

    @RequestMapping(value = {"add/user"}, method = RequestMethod.POST)
    private View addUser(Map<String, Object> model, HttpSession session, 
            UserForm userForm) {

        Long tokenId = (Long) session.getAttribute("tokenId");

        Map<Long, Token> tokens = TokenRegistrationController.getTokenDatabase();

        Token token = tokens.get(tokenId);

        User user = new User();
        user.setFirstName(userForm.getFirstName());
        user.setMiddleName(userForm.getMiddleName());
        user.setLastName(userForm.getLastName());

        String birthDate = userForm.getBirthDate();
        LocalDate bdate;
        try {
            bdate = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            // check date less than today date
            user.setBirthDate(bdate);

            user.setEmail(userForm.getEmail());
            user.setPassword(userForm.getPassword());

            token.setActivated(true);
            token.setActivatedDate(Instant.now());
            token.setUser(user);

            return new RedirectView("/token/user/view", true, false);

        } catch (DateTimeParseException e) {
            String msg = "Неправильный формат даты. "
                    + "Введите дату в следующем формате '30.03.1985'.";
            model.put("message", msg);
            model.put("uuidString", token.getUuidString());
            return new RedirectView("/token/view/error", true, true, true);
        }

    }

    @RequestMapping(value = {"user/edit"}, method = RequestMethod.GET)
    public String edit(Map<String, Object> model, HttpSession session) {

        Long tokenId = (Long) session.getAttribute("tokenId");

        Map<Long, Token> tokens = TokenRegistrationController.getTokenDatabase();

        Token token = tokens.get(tokenId);

        User user = token.getUser();
        UserForm form = new UserForm();
        form.setEmail(user.getEmail());
        form.setFirstName(user.getFirstName());
        form.setMiddleName(user.getMiddleName());
        form.setLastName(user.getLastName());
        form.setBirthDate(user.getBirthDate() == null
                ? "" : user.getBirthDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        );
        form.setPassword(user.getPassword());
        model.put("userForm", form);
        model.put("token", token);
        return "user/edit/edit";
    }

    @RequestMapping(value = {"user/edit"}, method = RequestMethod.POST)
    public View edit(Map<String, Object> model, HttpSession session, UserForm form) {

        Long tokenId = (Long) session.getAttribute("tokenId");

        Map<Long, Token> tokens = TokenRegistrationController.getTokenDatabase();

        Token token = tokens.get(tokenId);

        User user = token.getUser();
        user.setEmail(form.getEmail());
        user.setPassword(form.getPassword());
        user.setFirstName(form.getFirstName());
        user.setMiddleName(form.getMiddleName());
        user.setLastName(form.getLastName());
        String birthDate = form.getBirthDate();
        LocalDate bdate;
        try {
            bdate = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        } catch (DateTimeParseException e) {
            String msg = "Неправильный формат даты. "
                    + "Введите дату в следующем формате '30.03.1985'.";
            model.put("message", msg);
            model.put("uuidString", token.getUuidString());
            return new RedirectView("/token/view/error", true, true, true);
        }
        // check date less than today date
        user.setBirthDate(bdate);

        return new RedirectView("/token/user/view", true, false);

    }

    public static class UserForm {

        private String email;
        private String password;
        private String firstName;
        private String lastName;
        private String middleName;
        private String birthDate;
        
        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

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

        public String getBirthDate() {
            return birthDate;
        }

        public void setBirthDate(String birthDate) {
            this.birthDate = birthDate;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

    }
}
