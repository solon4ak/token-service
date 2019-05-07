package ru.tokens.site.controller;

import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import ru.tokens.site.entities.Contact;
import ru.tokens.site.entities.Token;
import ru.tokens.site.entities.User;
import ru.tokens.site.services.UserService;

/**
 *
 * @author solon4ak
 */
@Controller
@RequestMapping("token/user/contact")
public class ContactController {

    @Autowired
    private UserService userService;

    private static final Logger log = LogManager.getLogger("ContactController");

    private volatile long CONTACT_ID_SEQUENCE = 1;

    private synchronized long getNextContactId() {
        return this.CONTACT_ID_SEQUENCE++;
    }

    @RequestMapping(value = {"add"}, method = RequestMethod.GET)
    public ModelAndView addContact(Map<String, Object> model, HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return new ModelAndView(new RedirectView("/login", true, false));
        }
        User user = userService.findUserById(userId);

        Map<Long, Token> tokens = TokenRegistrationController.getTokenDatabase();

        Token token = tokens.get(user.getToken().getTokenId());
        if (token == null || !token.isActivated()) {
            return new ModelAndView(new RedirectView("/token/register", true, false));
        }

        model.put("contactForm", new ContactForm());
        model.put("token", token);
        model.put("user", user);

        return new ModelAndView("contact/edit/add");
    }

    @RequestMapping(value = {"add"}, method = RequestMethod.POST)
    public View addContact(HttpSession session, ContactForm form) {

        Contact contact = new Contact();
        contact.setContactId(this.getNextContactId());
        contact.setFirstName(form.getFirstName());
        contact.setLastName(form.getLastName());
        contact.setPhoneNumber(form.getPhoneNumber());
        contact.setEmail(form.getEmail());

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return new RedirectView("/login", true, false);
        }
        User user = userService.findUserById(userId);

        Map<Long, Token> tokens = TokenRegistrationController.getTokenDatabase();

        Token token = tokens.get(user.getToken().getTokenId());
        if (token == null || !token.isActivated()) {
            return new RedirectView("/token/register", true, false);
        }

        user.addContact(contact);

        log.info("Contact for token '{}' was added", token.getTokenId());
        return new RedirectView("/token/user/view", true, false);
    }

    @RequestMapping(value = {"delete/{contactId}"}, method = RequestMethod.GET)
    public View delete(HttpSession session, @PathVariable("contactId") Long contactId) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return new RedirectView("/login", true, false);
        }
        User user = userService.findUserById(userId);

        Map<Long, Token> tokens = TokenRegistrationController.getTokenDatabase();

        Token token = tokens.get(user.getToken().getTokenId());
        if (token == null || !token.isActivated()) {
            return new RedirectView("/token/register", true, false);
        }

        user.deleteContact(contactId);

        log.info("Contact '{}' for token '{}' was deleted.", contactId, token.getTokenId());
        return new RedirectView("/token/user/view", true, false);
    }

    @RequestMapping(value = {"edit/{contactId}"}, method = RequestMethod.GET)
    public ModelAndView editContact(Map<String, Object> model, HttpSession session,
            @PathVariable(value = "contactId") Long contactId) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return new ModelAndView(new RedirectView("/login", true, false));
        }
        User user = userService.findUserById(userId);

        Map<Long, Token> tokens = TokenRegistrationController.getTokenDatabase();

        Token token = tokens.get(user.getToken().getTokenId());
        if (token == null || !token.isActivated()) {
            return new ModelAndView(new RedirectView("/token/register", true, false));
        }

        ContactForm form = new ContactForm();
        Contact contact = user.getContact(contactId);

        if (contact != null) {
            form.setFirstName(contact.getFirstName());
            form.setLastName(contact.getLastName());
            form.setPhoneNumber(contact.getPhoneNumber());
            form.setEmail(contact.getEmail());
            model.put("contactForm", form);
            model.put("contact", contact);
            model.put("token", token);
            model.put("user", user);
            log.info("Editing contact for token '{}'.", token.getTokenId());
            return new ModelAndView("contact/edit/edit");
        }
        log.info("Editing contact for token '{}' goes wrong.", token.getTokenId());

        model.put("contactForm", new ContactForm());
        return new ModelAndView("contact/edit/add");
    }

    @RequestMapping(value = {"edit/{contactId}"}, method = RequestMethod.POST)
    public View editContact(HttpSession session, ContactForm form,
            @PathVariable(value = "contactId") Long contactId) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return new RedirectView("/login", true, false);
        }
        User user = userService.findUserById(userId);

        Map<Long, Token> tokens = TokenRegistrationController.getTokenDatabase();

        Token token = tokens.get(user.getToken().getTokenId());
        if (token == null || !token.isActivated()) {
            return new RedirectView("/token/register", true, false);
        }

        Contact contact = user.getContact(contactId);

        if (contact != null) {
            contact.setFirstName(form.getFirstName());
            contact.setLastName(form.getLastName());
            contact.setPhoneNumber(form.getPhoneNumber());
            contact.setEmail(form.getEmail());

            log.info("Contact for token '{}' has been edited", token.getTokenId());
        }

        log.info("Contact for token '{}' hasn't been edited", token.getTokenId());
        return new RedirectView("/token/user/view", true, false);
    }

    public static class ContactForm {

        private String firstName;
        private String lastName;
        private String email;
        private String phoneNumber;

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

    }
}
