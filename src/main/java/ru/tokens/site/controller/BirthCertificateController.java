package ru.tokens.site.controller;

import java.security.Principal;
import java.time.LocalDate;
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
import ru.tokens.site.entities.BirthCertificate;
import ru.tokens.site.entities.Token;
import ru.tokens.site.entities.User;
import ru.tokens.site.services.TokenService;
import ru.tokens.site.services.UserService;

/**
 *
 * @author solon4ak
 */
@Controller
@RequestMapping("token/user/birthcert")
public class BirthCertificateController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    private static final Logger log = LogManager.getLogger("Birth Certificate");

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public ModelAndView getCertificateForm(Map<String, Object> model, Principal principal) {

        Long userId = Long.valueOf(principal.getName());
        User user = userService.findUserById(userId);
        Token token = tokenService.findTokenByUser(user);

        if (token == null || !token.isActivated()) {
            return new ModelAndView(new RedirectView("/token/register", true, false));
        }

        model.put("token", token);
        model.put("user", user);
        model.put("certForm", new BthCertForm());
        return new ModelAndView("birth_certificate/edit/add");
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public View addCertificate(Principal principal, BthCertForm form) {

        Long userId = Long.valueOf(principal.getName());
        User user = userService.findUserById(userId);
        Token token = tokenService.findTokenByUser(user);

        if (token == null || !token.isActivated()) {
            return new RedirectView("/token/register", true, false);
        }

        BirthCertificate certificate = new BirthCertificate();
        certificate.setSeries(form.getSeries());
        certificate.setNumber(form.getNumber());
        certificate.setIssueDepartment(form.getIssueDepartment());

        certificate.setSeries(form.getSeries());

        String issueDate = form.getIssueDate();
        LocalDate iDate = null;

        try {
            iDate = LocalDate.parse(issueDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        } catch (DateTimeParseException e) {
            log.warn("Wrong date format '{}'", issueDate, e);
        }

        certificate.setIssueDate(iDate);

        user.setBirthCertificate(certificate);

        log.info("Birth Certificate for token '{}' was added", token.getTokenId());
        return new RedirectView("/token/user/view", true, false);
    }

    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public ModelAndView editCertificate(Map<String, Object> model, Principal principal) {

        Long userId = Long.valueOf(principal.getName());
        User user = userService.findUserById(userId);
        Token token = tokenService.findTokenByUser(user);
        
        if (token == null || !token.isActivated()) {
            return new ModelAndView(new RedirectView("/token/register", true, false));
        }

        BirthCertificate certificate = user.getBirthCertificate();
        BthCertForm form = new BthCertForm();
        form.setSeries(certificate.getSeries());
        form.setNumber(certificate.getNumber());
        form.setIssueDepartment(certificate.getIssueDepartment());

        form.setIssueDate(certificate.getIssueDate() == null
                ? "" : certificate.getIssueDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        );

        model.put("token", token);
        model.put("certForm", form);
        model.put("user", user);
        return new ModelAndView("birth_certificate/edit/edit");
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public View editCertificate(Principal principal, BthCertForm form) {

        Long userId = Long.valueOf(principal.getName());
        User user = userService.findUserById(userId);
        Token token = tokenService.findTokenByUser(user);
        
        if (token == null || !token.isActivated()) {
            return new RedirectView("/token/register", true, false);
        }

        BirthCertificate certificate = new BirthCertificate();
        certificate.setSeries(form.getSeries());
        certificate.setNumber(form.getNumber());
        certificate.setIssueDepartment(form.getIssueDepartment());

        certificate.setSeries(form.getSeries());

        String issueDate = form.getIssueDate();
        LocalDate iDate = null;

        try {
            iDate = LocalDate.parse(issueDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        } catch (DateTimeParseException e) {
            log.warn("Wrong date format '{}'", issueDate, e);
        }

        certificate.setIssueDate(iDate);

        user.setBirthCertificate(certificate);

        log.info("Birth Certificate for token '{}' has been edited", token.getTokenId());
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

        user.setBirthCertificate(null);

        log.info("Birth Certificate for token '{}' has been deleted", token.getTokenId());
        return new RedirectView("/token/user/view", true, false);
    }

    public static class BthCertForm {

        private String series;
        private String number;
        private String issueDepartment;
        private String issueDate;

        public String getSeries() {
            return series;
        }

        public void setSeries(String series) {
            this.series = series;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getIssueDepartment() {
            return issueDepartment;
        }

        public void setIssueDepartment(String issueDepartment) {
            this.issueDepartment = issueDepartment;
        }

        public String getIssueDate() {
            return issueDate;
        }

        public void setIssueDate(String issueDate) {
            this.issueDate = issueDate;
        }
    }
}
