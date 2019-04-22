package ru.tokens.site.controller;

import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import ru.tokens.site.entities.Contact;
import ru.tokens.site.entities.Token;
import ru.tokens.site.entities.User;

/**
 *
 * @author solon4ak
 */
@Controller
@RequestMapping("token/user/contact")
public class ContactController {

    private static final Logger log = LogManager.getLogger("ContactController");

    private volatile long CONTACT_ID_SEQUENCE = 1;

    private synchronized long getNextContactId() {
        return this.CONTACT_ID_SEQUENCE++;
    }

    @RequestMapping(value = {"add"}, method = RequestMethod.GET)
    public String addContact(Map<String, Object> model) {
        model.put("contactForm", new ContactForm());
        return "contact/edit/add";
    }

    @RequestMapping(value = {"add"}, method = RequestMethod.POST)
    public View addContact(HttpSession session, ContactForm form) {

        Contact contact = new Contact();
        contact.setContactId(this.getNextContactId());
        contact.setFirstName(form.getFirstName());
        contact.setLastName(form.getLastName());
        contact.setPhoneNumber(form.getPhoneNumber());
        contact.setEmail(form.getEmail());

        Long tokenId = (Long) session.getAttribute("tokenId");
        Map<Long, Token> tokens = TokenRegistrationController.getTokenDatabase();

        Token token = tokens.get(tokenId);
        User user = token.getUser();
        user.addContact(contact);

        log.info("Contact for token '{}' was added", tokenId);
        return new RedirectView("/token/user/view", true, false);
    }

    @RequestMapping(value = {"delete/{contactId}"}, method = RequestMethod.GET)
    public View delete(HttpSession session, @PathVariable("contactId") Long contactId) {

        Long tokenId = (Long) session.getAttribute("tokenId");
        Map<Long, Token> tokens = TokenRegistrationController.getTokenDatabase();

        Token token = tokens.get(tokenId);
        User user = token.getUser();

        Contact contact = null;

        if (user != null) {
            user.deleteContact(contactId);
        }

        log.info("Contact '{}' for token '{}' was deleted.", contactId, tokenId);
        return new RedirectView("/token/user/view", true, false);
    }

    @RequestMapping(value = {"edit/{contactId}"}, method = RequestMethod.GET)
    public String editContact(Map<String, Object> model, HttpSession session,
            @PathVariable(value = "contactId") Long contactId) {

        Long tokenId = (Long) session.getAttribute("tokenId");
        Map<Long, Token> tokens = TokenRegistrationController.getTokenDatabase();

        Token token = tokens.get(tokenId);
        User user = token.getUser();

        ContactForm form = new ContactForm();
        Contact contact = null;

        if (user != null) {
//            List<Contact> contacts = user.getContacts();
//            if (contacts != null && contacts.size() > 0) {
//                for (Contact c : contacts) {
//                    if (contactId.equals(c.getContactId())) {
//                        contact = c;
//                    }
//                }
//            }

            contact = user.getContact(contactId);

            if (contact != null) {
                form.setFirstName(contact.getFirstName());
                form.setLastName(contact.getLastName());
                form.setPhoneNumber(contact.getPhoneNumber());
                form.setEmail(contact.getEmail());
                model.put("contactForm", form);
                model.put("contact", contact);
                log.info("Editing contact for token '{}'.", tokenId);
                return "contact/edit/edit";
            }
            log.info("Editing contact for token '{}' goes wrong.", tokenId);
        }
        return "contact/edit/add";
    }

    @RequestMapping(value = {"edit/{contactId}"}, method = RequestMethod.POST)
    public View editContact(HttpSession session, ContactForm form,
            @PathVariable(value = "contactId") Long contactId) {

        Long tokenId = (Long) session.getAttribute("tokenId");
        Map<Long, Token> tokens = TokenRegistrationController.getTokenDatabase();

        Token token = tokens.get(tokenId);
        User user = token.getUser();

        Contact contact = null;

        if (user != null) {
//            List<Contact> contacts = user.getContacts();
//            if (contacts != null && contacts.size() > 0) {
//                for (Contact c : contacts) {
//                    if (contactId.equals(c.getContactId())) {
//                        contact = c;
//                    }
//                }
//            }

            contact = user.getContact(contactId);

            if (contact != null) {
                contact.setFirstName(form.getFirstName());
                contact.setLastName(form.getLastName());
                contact.setPhoneNumber(form.getPhoneNumber());
                contact.setEmail(form.getEmail());

                log.info("Contact for token '{}' has been edited", tokenId);
            }
        }

        log.info("Contact for token '{}' hasn't been edited", tokenId);
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
