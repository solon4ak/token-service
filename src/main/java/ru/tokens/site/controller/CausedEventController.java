package ru.tokens.site.controller;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import ru.tokens.site.entities.Attachment;
import ru.tokens.site.entities.CausedEvent;
import ru.tokens.site.entities.Contact;
import ru.tokens.site.entities.DataEntry;
import ru.tokens.site.entities.Token;
import ru.tokens.site.entities.User;
import ru.tokens.site.utils.FileUtil;

/**
 *
 * @author solon4ak
 */
@Controller
@RequestMapping("token/user/csdevent")
public class CausedEventController {

    private static final Logger log = LogManager.getLogger(CausedEventController.class);    
    
    @Autowired
    @Qualifier("fileService")
    private FileUtil fileUtil;

    private volatile long MESSAGE_ID_SEQUENCE = 1;

    private synchronized long getNextMessageId() {
        return this.MESSAGE_ID_SEQUENCE++;
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String getCausedEventForm(Map<String, Object> model, HttpSession session) {
        Token token = this.getToken(session);
        if (null == token) {
            return "/login";
        }
        User user = token.getUser();
        List<Contact> contacts = user.getContacts();
        model.put("contacts", contacts);
        model.put("emailIntervals", this.getIntervals());
        model.put("eventForm", new EventForm());
        return "causedevent/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public View getCausedEventForm(Map<String, Object> model,
            HttpSession session, EventForm form) throws IOException {
        Token token = this.getToken(session);
        if (null == token) {
            return new RedirectView("/login", true, false);
        }
        User user = token.getUser();
        CausedEvent messageEvent = new CausedEvent();
        messageEvent.setId(this.getNextMessageId());
        messageEvent.setUser(user);

        switch (form.getEmailSendingInterval()) {
            case ("Week"):
                messageEvent.setEmailsInterval(7);
                break;
            case ("Month"):
                messageEvent.setEmailsInterval(30);
                break;
            case ("3 Months"):
                messageEvent.setEmailsInterval(91);
                break;
            case ("6 Months"):
                messageEvent.setEmailsInterval(182);
                break;
            case ("Year"):
                messageEvent.setEmailsInterval(365);
                break;
            default:
                messageEvent.setEmailsInterval(91);
                break;
        }
        
        messageEvent.setContacts(form.getEmailContacts());
        
        DataEntry entry = new DataEntry();
        entry.setDateCreated(Instant.now());
        entry.setSubject(form.getSubject());
        entry.setBody(form.getBody());
        
        for (MultipartFile filePart : form.getAttachments()) {
            log.debug("Processing attachment for new entry.");
            Attachment attachment = new Attachment();
            attachment.setName(filePart.getOriginalFilename());
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setContents(filePart.getBytes());

            if ((attachment.getName() != null && attachment.getName().length() > 0)
                    || (attachment.getContents() != null && attachment.getContents().length > 0)) {

                String newFileName = fileUtil.getNewFileName(filePart);
                String storageDirectory = fileUtil.getStorageDirectory(token);
                File newFile = new File(storageDirectory + newFileName);
                try {
                    filePart.transferTo(newFile);

                    attachment.setId(this.getNextAttachmentId());
                    attachment.setName(filePart.getOriginalFilename());
                    attachment.setNewFileName(newFileName);
                    attachment.setContentType(filePart.getContentType());
                    attachment.setSize(filePart.getSize());

                    entry.addAttachment(attachment);
                } catch (IOException
                        | IllegalArgumentException | IllegalStateException e) {
                    log.error("Could not upload file " + filePart.getOriginalFilename(), e);
                }
            }
        }

        messageEvent.setDataEntry(entry);        

        user.addActionOnCondition(messageEvent);
        return new RedirectView("causedevent/view", true, false);
    }

    private Token getToken(HttpSession session) {
        Map<Long, Token> tokens = TokenRegistrationController.getTokenDatabase();
        Long tokenId = (Long) session.getAttribute("tokenId");
        Token token = tokens.get(tokenId);
        
        return token;
    }

    private List<String> getIntervals() {
        List<String> intervals = new LinkedList<>();
        intervals.addAll(Arrays.asList(
                "Week", "Month", "3 Months",
                "6 Months", "Year"
        ));
        return intervals;
    }

    public static class EventForm {

        private String subject;
        private String body;
        private List<MultipartFile> attachments;
        private List<Contact> emailContacts;
        private String emailSendingInterval;

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public List<MultipartFile> getAttachments() {
            return attachments;
        }

        public void setAttachments(List<MultipartFile> attachments) {
            this.attachments = attachments;
        }

        public List<Contact> getEmailContacts() {
            return emailContacts;
        }

        public void setEmailContacts(List<Contact> emailContacts) {
            this.emailContacts = emailContacts;
        }

        public String getEmailSendingInterval() {
            return emailSendingInterval;
        }

        public void setEmailSendingInterval(String emailSendingInterval) {
            this.emailSendingInterval = emailSendingInterval;
        }

    }
}
