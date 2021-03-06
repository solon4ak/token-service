package ru.tokens.site.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import ru.tokens.site.entities.Passport;
import ru.tokens.site.entities.Token;
import ru.tokens.site.entities.User;
import ru.tokens.site.exceptions.PassportIssueDateException;
import ru.tokens.site.services.TokenService;
import ru.tokens.site.services.UserService;

/**
 *
 * @author solon4ak
 */
@Controller
@RequestMapping("token/user/passport")
public class PassportController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private TokenService tokenService;

    private static final Logger log = LogManager.getLogger("Passport");

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public ModelAndView getPassportForm(Map<String, Object> model, Principal principal) {

        Long userId = Long.valueOf(principal.getName());
        User user = userService.findUserById(userId);

        Token token = tokenService.findTokenByUser(user);
        if (token == null || !token.isActivated()) {
            return new ModelAndView(new RedirectView("/token/register", true, false));
        }

        model.put("token", token);
        model.put("passportForm", new PassportForm());
        model.put("user", user);
        return new ModelAndView("passport/edit/add");
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public View addPassport(Principal principal, PassportForm form) {

        Long userId = Long.valueOf(principal.getName());
        User user = userService.findUserById(userId);

        Token token = tokenService.findTokenByUser(user);
        if (token == null || !token.isActivated()) {
            return new RedirectView("/token/register", true, false);
        }

        Passport passport = new Passport();
        passport.setSeries(form.getSer());
        passport.setNumber(form.getNum());
        passport.setIssueDepartment(form.getIssueDep());
        passport.setIssueDepartmentCode(form.getIssueDepCode());

        String issueDate = form.getDated();
        LocalDate iDate = null;

        try {
            iDate = LocalDate.parse(issueDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        } catch (DateTimeParseException e) {
            log.warn("Wrong date format '{}'", issueDate, e);
        }

        if (!this.checkPassportIssueDate(user, iDate)) {
            String msg = "Указанный возраст получения паспорта менее 14 лет!";
            throw new PassportIssueDateException(msg);
        }

        passport.setIssueDate(iDate);

        user.setPassport(passport);

        log.info("Passport for token '{}' was added", token.getTokenId());
        return new RedirectView("/token/user/view", true, false);
    }

    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public ModelAndView editPassport(Map<String, Object> model, Principal principal) {

        Long userId = Long.valueOf(principal.getName());
        User user = userService.findUserById(userId);

        Token token = tokenService.findTokenByUser(user);
        if (token == null || !token.isActivated()) {
            return new ModelAndView(new RedirectView("/token/register", true, false));
        }

        Passport passport = user.getPassport();

        PassportForm pForm = new PassportForm();
        pForm.setSer(passport.getSeries());
        pForm.setNum(passport.getNumber());
        pForm.setIssueDep(passport.getIssueDepartment());
        pForm.setIssueDepCode(passport.getIssueDepartmentCode());
        pForm.setDated(passport.getIssueDate() == null
                ? "" : passport.getIssueDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        );

        model.put("token", token);
        model.put("passportForm", pForm);
        model.put("user", user);
        return new ModelAndView("passport/edit/edit");
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public View editPassport(Principal principal, PassportForm form) {

        Long userId = Long.valueOf(principal.getName());
        User user = userService.findUserById(userId);

        Token token = tokenService.findTokenByUser(user);
        if (token == null || !token.isActivated()) {
            return new RedirectView("/token/register", true, false);
        }

        Passport passport = user.getPassport();

        passport.setSeries(form.getSer());
        passport.setNumber(form.getNum());
        passport.setIssueDepartment(form.getIssueDep());
        passport.setIssueDepartmentCode(form.getIssueDepCode());

        String issueDate = form.getDated();
        LocalDate iDate = null;

        try {
            iDate = LocalDate.parse(issueDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        } catch (DateTimeParseException e) {
            log.warn("Wrong date format '{}'", issueDate, e);
        }

        if (!this.checkPassportIssueDate(user, iDate)) {
            String msg = "Указанный возраст получения паспорта менее 14 лет!";
            throw new PassportIssueDateException(msg);
        }

        passport.setIssueDate(iDate);

        log.info("Passport for token '{}' has been edited", token.getTokenId());
        return new RedirectView("/token/user/view", true, false);
    }

    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public View deletePassport(Principal principal) {
          
        Long userId = Long.valueOf(principal.getName());
        User user = userService.findUserById(userId);

        Token token = tokenService.findTokenByUser(user);
        if (token == null || !token.isActivated()) {
            return new RedirectView("/token/register", true, false);
        }
        
        user.setPassport(null);

        log.info("Passport for token '{}' has been deleted", token.getTokenId());
        return new RedirectView("/token/user/view", true, false);
    }

    private synchronized boolean checkPassportIssueDate(final User user, final LocalDate issueDate) {
        LocalDate birthDate = user.getBirthDate();
        int diff = Period.between(birthDate, issueDate).getYears();
        return diff >= 14;
    }

    public static class PassportForm {

        private String ser;
        private String num;
        private String issueDep;
        private String issueDepCode;
        private String dated;

        public String getSer() {
            return ser;
        }

        public void setSer(String ser) {
            this.ser = ser;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getIssueDep() {
            return issueDep;
        }

        public void setIssueDep(String issueDep) {
            this.issueDep = issueDep;
        }

        public String getIssueDepCode() {
            return issueDepCode;
        }

        public void setIssueDepCode(String issueDepCode) {
            this.issueDepCode = issueDepCode;
        }

        public String getDated() {
            return dated;
        }

        public void setDated(String dated) {
            this.dated = dated;
        }

    }
}
