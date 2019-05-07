package ru.tokens.site.controller;

import java.time.LocalDate;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ru.tokens.site.entities.Token;
import ru.tokens.site.entities.User;
import ru.tokens.site.services.TokenService;
import ru.tokens.site.services.UserService;
import ru.tokens.site.utils.TimeUtils;

/**
 *
 * @author solon4ak
 */
@Controller
@RequestMapping("token")
public class UserDataController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private TokenService tokenService;

    private static final Logger log = LogManager.getLogger("TokenUserData");

    @RequestMapping(value = "{tokenId}/{uuidString}", method = RequestMethod.GET)
    public ModelAndView view(Map<String, Object> model,
            @PathVariable("tokenId") Long tokenId,
            @PathVariable("uuidString") String uuidString,
            @RequestParam("showMH") boolean showMH) {
        
        String msg;

        if (tokenId == null || uuidString == null) {
            msg = "Такой жетон не существует";
            model.put("message", msg);
            model.put("uuidString", uuidString);
            return new ModelAndView("token/view/error");
        }

        Token token = tokenService.findTokenById(tokenId);

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
        model.put("user", user);

        System.out.println("--- goes to view page for outer context for tokenId " + tokenId);
        if (showMH) {
            return new ModelAndView("user/view/view");
        }
        return new ModelAndView("user/view/address");
    }

    @RequestMapping(value = "user/view", method = RequestMethod.GET)
    public ModelAndView view(Map<String, Object> model, HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return new ModelAndView(new RedirectView("/login", true, false));
        }
        User user = userService.findUserById(userId);

        Token token = tokenService.findTokenByUser(user);
        if (token == null || !token.isActivated()) {
            return new ModelAndView(new RedirectView("/token/register", true, false));
        }

        model.put("token", token);
        model.put("user", user);
        System.out.println("--- goes to view page for registered user with tokenId " + token.getTokenId());
        return new ModelAndView("user/edit/view");
    }

//    @RequestMapping(value = {"add/user"}, method = RequestMethod.GET)
//    public ModelAndView addUserForm(Map<String, Object> model, HttpSession session) {
//
//        Long userId = (Long) session.getAttribute("userId");
//        if (userId == null) {
//            return new ModelAndView(new RedirectView("/login", true, false));
//        }
//        User user = userRegistrationController.getUserDatabase().get(userId);
//
//        Map<Long, Token> tokens = TokenRegistrationController.getTokenDatabase();
//        Token token = tokens.get(user.getToken().getTokenId());
//        if (token == null || !token.isActivated()) {
//            return new ModelAndView(new RedirectView("/token/register", true, false));
//        }
//
//        UserForm userForm = new UserForm();
//        userForm.setFirstName(user.getFirstName());
//        userForm.setLastName(user.getLastName());
//        userForm.setMiddleName(user.getMiddleName());
//        
//        model.put("userForm", userForm);
//        model.put("token", token);
//        model.put("user", user);
//        System.out.println("--- entering adding user form");
//        return new ModelAndView("user/edit/add");
//    }

//    @RequestMapping(value = {"add/user"}, method = RequestMethod.POST)
//    private View addUser(Map<String, Object> model, HttpSession session,
//            UserForm userForm) {
//
//        Long userId = (Long) session.getAttribute("userId");
//        if (userId == null) {
//            return new RedirectView("/login", true, false);
//        }
//        User user = userRegistrationController.getUserDatabase().get(userId);
//
//        Map<Long, Token> tokens = TokenRegistrationController.getTokenDatabase();
//        Token token = tokens.get(user.getToken().getTokenId());
//        if (token == null || !token.isActivated()) {
//            return new RedirectView("/token/register", true, false);
//        }
//
//        user.setFirstName(userForm.getFirstName());
//        user.setMiddleName(userForm.getMiddleName());
//        user.setLastName(userForm.getLastName());
//
//        String birthDate = userForm.getBirthDate();
//        LocalDate bdate;
//        try {
//            bdate = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
//            // check date less than today date
//            user.setBirthDate(bdate);
//
//        } catch (DateTimeParseException e) {
//            String msg = "Неправильный формат даты. "
//                    + "Введите дату в следующем формате '30.03.1985'.";
//            model.put("message", msg);
//            model.put("uuidString", token.getUuidString());
//            return new RedirectView("/token/view/error", true, true, true);
//        }
//
//        return new RedirectView("/token/user/view", true, false);
//    }

//    @RequestMapping(value = {"user/edit"}, method = RequestMethod.GET)
//    public ModelAndView edit(Map<String, Object> model, HttpSession session) {
//
//        Long userId = (Long) session.getAttribute("userId");
//        if (userId == null) {
//            return new ModelAndView(new RedirectView("/login", true, false));
//        }
//        User user = userRegistrationController.getUserDatabase().get(userId);
//
//        Map<Long, Token> tokens = TokenRegistrationController.getTokenDatabase();
//        Token token = tokens.get(user.getToken().getTokenId());
//        if (token == null || !token.isActivated()) {
//            return new ModelAndView(new RedirectView("/token/register", true, false));
//        }
//
//        UserForm form = new UserForm();
//        form.setFirstName(user.getFirstName());
//        form.setMiddleName(user.getMiddleName());
//        form.setLastName(user.getLastName());
//        form.setBirthDate(user.getBirthDate() == null
//                ? "" : user.getBirthDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
//        );
//
//        model.put("userForm", form);
//        model.put("token", token);
//        model.put("user", user);
//        
//        return new ModelAndView("user/edit/edit");
//    }
//
//    @RequestMapping(value = {"user/edit"}, method = RequestMethod.POST)
//    public View edit(Map<String, Object> model, HttpSession session, UserForm form) {
//
//        Long userId = (Long) session.getAttribute("userId");
//        if (userId == null) {
//            return new RedirectView("/login", true, false);
//        }
//        User user = userRegistrationController.getUserDatabase().get(userId);
//
//        Map<Long, Token> tokens = TokenRegistrationController.getTokenDatabase();
//        Token token = tokens.get(user.getToken().getTokenId());
//        if (token == null || !token.isActivated()) {
//            return new RedirectView("/token/register", true, false);
//        }
//
//        user.setFirstName(form.getFirstName());
//        user.setMiddleName(form.getMiddleName());
//        user.setLastName(form.getLastName());
//        String birthDate = form.getBirthDate();
//        LocalDate bdate;
//        try {
//            bdate = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
//        } catch (DateTimeParseException e) {
//            String msg = "Неправильный формат даты. "
//                    + "Введите дату в следующем формате '30.03.1985'.";
//            model.put("message", msg);
//            model.put("uuidString", token.getUuidString());
//            return new RedirectView("/token/view/error", true, true, true);
//        }
//        // check date less than today date
//        user.setBirthDate(bdate);
//
//        return new RedirectView("/token/user/view", true, false);
//
//    }

//    public static class UserForm {
//
//        private String firstName;
//        private String lastName;
//        private String middleName;
//        private String birthDate;
//
//        public String getFirstName() {
//            return firstName;
//        }
//
//        public void setFirstName(String firstName) {
//            this.firstName = firstName;
//        }
//
//        public String getLastName() {
//            return lastName;
//        }
//
//        public void setLastName(String lastName) {
//            this.lastName = lastName;
//        }
//
//        public String getMiddleName() {
//            return middleName;
//        }
//
//        public void setMiddleName(String middleName) {
//            this.middleName = middleName;
//        }
//
//        public String getBirthDate() {
//            return birthDate;
//        }
//
//        public void setBirthDate(String birthDate) {
//            this.birthDate = birthDate;
//        }
//
//    }
}
