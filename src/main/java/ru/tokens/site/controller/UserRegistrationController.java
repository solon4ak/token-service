package ru.tokens.site.controller;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import ru.tokens.site.entities.User;
import ru.tokens.site.registration.OnRegistrationCompleteEvent;
import ru.tokens.site.services.UserService;
import ru.tokens.site.services.EmailSender;
import ru.tokens.site.services.PasswordUtil;

/**
 *
 * @author solon4ak
 */
@Controller
@RequestMapping(value = "user")
public class UserRegistrationController {

    @Autowired
    private Environment environment;

    @Autowired
    @Qualifier(value = "passayPasswordUtil")
    private PasswordUtil passwordUtil;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailSender emailSender;

    private static final Logger log = LogManager.getLogger("User");

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String register(Map<String, Object> model) {
        UserRegistrationForm form = new UserRegistrationForm();
        String password = passwordUtil.generatePassword();
        form.setPassword(password);
        log.info("Password generated {}", password);
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

        // Проверка на наличие этого почтового адреса в базе
        String email = form.getEmail();
        if (userService.findUserByEmail(email) != null) {
            String msg = "User with email: " + email + " has already registered.";
            model.put("message", msg);
            model.put("userRegistrationFailed", true);
            form.setEmail("");
            model.put("userRegistrationForm", form);
            return new ModelAndView("userreg/registration");
        }

        String birthDate = form.getBirthDate();
        LocalDate bdate;
        try {
            bdate = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        } catch (DateTimeParseException e) {
            String msg = "Неправильный формат даты. "
                    + "Введите дату в следующем формате '30.03.1985'.";
            model.put("message", msg);
            model.put("userRegistrationFailed", true);
            model.put("userRegistrationForm", form);
            return new ModelAndView("userreg/registration");
        }

        User user = new User();
        user.setRegistered(Instant.now());
        user.setLastName(lastName);
        user.setFirstName(firstName);
        user.setMiddleName(midName);
        user.setUserEmailAddress(email);
        user.setPassword(password);
        user.setPhoneNumber(phoneNumber);
        // check date less than today date
        user.setBirthDate(bdate);

        try {
            String appUrl = environment.getProperty("host.name") + request.getContextPath();
            eventPublisher.publishEvent(
                    new OnRegistrationCompleteEvent(
                            user,
                            request.getLocale(),
                            appUrl
                    )
            );
            log.info("Event published for User: {}", user.toString());
        } catch (Exception me) {
            String msg = "Почтовый ящик не подтвержден!";
            model.put("message", msg);
//            model.put("user", user);
            return new ModelAndView("userreg/error");
        }        

        this.userService.saveUser(user);
        log.warn("Registering user account with userId: {}", user.getUserId());
        log.info("User '{}' successfully registered.", user.getLastName() + ", " + user.getFirstName());
        
        return new ModelAndView(new RedirectView("/user/success", true, false));
    }

    @RequestMapping(value = "success")
    public String successRegistration(Map<String, Object> model, HttpServletRequest request) {
        String msg = "Завершите регистрацию перейдя по ссылке в полученном письме.";
        request.changeSessionId();
        model.put("message", msg);
        return "userreg/result";
    }

    @RequestMapping(value = "registrationConfirm", method = RequestMethod.GET)
    public ModelAndView confirmRegistration(Map<String, Object> model,
            HttpServletRequest request, HttpSession session,
            @RequestParam("token") String confimationToken) {

//        Locale locale = request.getLocale();
        final String result = userService.validateVerificationToken(confimationToken);
        final User user = userService.getUser(confimationToken);

        if (result.equals("valid")) {

            String subject = "Thank u for registration.";
            String email = user.getUserEmailAddress();
            String body = "Your e-mail: " + email
                    + "; password: " + user.getPassword();
            emailSender.sendSimpleEmail(email, subject, body);

            session.setAttribute("userId", user.getUserId());
            request.changeSessionId();
            return new ModelAndView(
                    new RedirectView("/user/view", true, true, true)
            );
        }
        /* Если пользователь не подтвердил почтовый адрес, пользователь удаляется из репозитория */
        this.userService.deleteUser(user);
        final String msg = "Почтовый ящик не подтвержден. Причина: " + result;
        model.put("message", msg);
//        model.put("user", user);
        return new ModelAndView("userreg/error");
    }

    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public ModelAndView editUserData(Map<String, Object> model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return new ModelAndView(new RedirectView("/login", true, false));
        }

        User user = this.userService.findUserById(userId);
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

        User user = this.userService.findUserById(userId);
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

        User user = this.userService.findUserById(userId);
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
