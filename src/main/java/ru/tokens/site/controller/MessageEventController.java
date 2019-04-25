package ru.tokens.site.controller;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import ru.tokens.site.entities.Attachment;
import ru.tokens.site.entities.MessageEvent;
import ru.tokens.site.entities.Contact;
import ru.tokens.site.entities.DataEntry;
import ru.tokens.site.entities.Token;
import ru.tokens.site.entities.User;
import ru.tokens.site.utils.EmailCheckingService;
import ru.tokens.site.utils.FileUtil;
import ru.tokens.site.utils.MessageEventHelper;

/**
 *
 * @author solon4ak
 */
@Controller
@RequestMapping("token/user/csdevent")
public class MessageEventController {

    private static final Logger log = LogManager.getLogger(MessageEventController.class);

    @Autowired
    @Qualifier("fileService")
    private FileUtil fileUtil;

    @Autowired
    @Qualifier("schedular")
    private EmailCheckingService schedular;

    private volatile long MESSAGE_ID_SEQUENCE = 1;

    private synchronized long getNextMessageId() {
        return this.MESSAGE_ID_SEQUENCE++;
    }

    private volatile long ENTRY_ID_SEQUENCE = 100;

    private synchronized long getNextEntryId() {
        return this.ENTRY_ID_SEQUENCE++;
    }

    private volatile long ATTACHMENT_ID_SEQUENCE = 100;

    private synchronized long getNextAttachmentId() {
        return this.ATTACHMENT_ID_SEQUENCE++;
    }

    @RequestMapping(value = "view/{eventId}", method = RequestMethod.GET)
    public String viewMessageEvent(Map<String, Object> model, HttpSession session,
            @PathVariable("eventId") Long eventId) {
        Token token = this.getToken(session);
        if (null == token) {
            return "/login";
        }
        User user = token.getUser();
        MessageEvent event = user.getMessageEvents().get(eventId);

        model.put("token", token);
        model.put("event", event);

        return "causedevent/view";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String getCausedEventForm(Map<String, Object> model, HttpSession session) {
        Token token = this.getToken(session);
        if (null == token) {
            return "/login";
        }
        User user = token.getUser();
        Collection<Contact> contacts = user.getContacts();

        model.put("contacts", contacts);
        model.put("emailIntervals", this.getIntervals());
        model.put("eventForm", new EventForm());
        return "causedevent/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public View getCausedEventForm(Map<String, Object> model,
            HttpSession session, EventForm form) throws IOException {
        System.out.println("--->Inside getCausedEventForm method");
        Token token = this.getToken(session);
        if (null == token) {
            return new RedirectView("/login", true, false);
        }

        User user = token.getUser();
        MessageEvent messageEvent = new MessageEvent();
        messageEvent.setId(this.getNextMessageId());
        messageEvent.setUser(user);

        messageEvent.setCheckingInterval(form.getEmailSendingInterval());

        Long[] contactIds = form.getEmailContacts();
        List<Contact> contacts = new LinkedList<>();

        if (contactIds != null && contactIds.length > 0) {
            for (Long id : contactIds) {
                contacts.add(user.getContact(id));
            }
        }

        messageEvent.setContacts(contacts);

        DataEntry entry = new DataEntry();
        entry.setDateCreated(Instant.now());
        entry.setSubject(form.getSubject());
        entry.setBody(form.getBody());
        entry.setId(this.getNextEntryId());

        this.uploadFiles(entry, form);

        messageEvent.setDataEntry(entry);
        user.addMessageEvent(messageEvent);

        messageEvent.setExecutor(schedular.createTimerService());
        schedular.runTimerService(messageEvent.getExecutor(), messageEvent);

        return new RedirectView("/token/user/csdevent/view/" + messageEvent.getId(), true, false);
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(Map<String, Object> model, HttpSession ssession) {

        Token token = this.getToken(ssession);
        User user = token.getUser();
        Collection<MessageEvent> messageEvents
                = new LinkedList<>(user.getMessageEventsList());

        model.put("token", token);
        model.put("messageEvents", messageEvents);
        return "causedevent/list";
    }

    @RequestMapping(value = "edit/{eventId}", method = RequestMethod.GET)
    public ModelAndView edit(Map<String, Object> model, HttpSession session,
            @PathVariable("eventId") Long eventId) {

        Token token = this.getToken(session);

        if (null == token) {
            return new ModelAndView(new RedirectView("/login", true, false));
        }

        User user = token.getUser();
        MessageEvent event = user.getMessageEvent(eventId);
        EventForm form = new EventForm();

        form.setSubject(event.getDataEntry().getSubject());
        form.setBody(event.getDataEntry().getBody());
        form.setEmailSendingInterval(event.getCheckingInterval());

        Collection<Contact> contacts = user.getContacts();
        List<Contact> checkedContacts = event.getContacts();
        Long[] contactsId = new Long[checkedContacts.size()];
        for (int i = 0; i < checkedContacts.size(); i++) {
            contactsId[i] = checkedContacts.get(i).getContactId();
        }
        form.setEmailContacts(contactsId);

        Collection<Attachment> attachments = event.getDataEntry().getAttachments();

        model.put("eventForm", form);
        model.put("token", token);
        model.put("event", event);
        model.put("contacts", contacts);
        model.put("attachments", attachments);
        model.put("emailIntervals", this.getIntervals());

        return new ModelAndView("causedevent/edit");
    }

    @RequestMapping(value = "edit/{eventId}", method = RequestMethod.POST)
    public View edit(HttpSession session, EventForm form,
            @PathVariable("eventId") long eventId) throws IOException {

        Token token = this.getToken(session);
        if (null == token) {
            return new RedirectView("/login", true, false);
        }

        User user = token.getUser();
        MessageEvent messageEvent = user.getMessageEvent(eventId);
//        messageEvent.setUser(user);

        messageEvent.setCheckingInterval(form.getEmailSendingInterval());

        Long[] contactIds = form.getEmailContacts();
        List<Contact> contacts = new LinkedList<>();

        if (contactIds != null && contactIds.length > 0) {
            for (Long id : contactIds) {
                contacts.add(user.getContact(id));
            }
        }

        messageEvent.setContacts(contacts);

        DataEntry entry = messageEvent.getDataEntry();

        this.uploadFiles(entry, form);

        messageEvent.setDataEntry(entry);
        user.addMessageEvent(messageEvent);

        // run messageEventTask
        return new RedirectView("/token/user/csdevent/view/" + messageEvent.getId(), true, false);
    }

    @RequestMapping(value = "delete/{eventId}", method = RequestMethod.GET)
    public View delete(HttpSession session, @PathVariable("eventId") Long eventId) {

        Token token = this.getToken(session);
        if (null == token) {
            return new RedirectView("/login", true, false);
        }

        User user = token.getUser();
        MessageEvent messageEvent = user.getMessageEvent(eventId);
        DataEntry entry = messageEvent.getDataEntry();

        if (null != entry) {
            Map<Long, Attachment> attachments = entry.getAttachmentsMap();
            if (!attachments.isEmpty()) {
//                String path = fileUtil.getStorageDirectory();
                for (Map.Entry<Long, Attachment> a : attachments.entrySet()) {
                    Attachment attachment = a.getValue();
//                    File attch = new File(path
//                            + File.separator
//                            + attachment.getNewFileName());
                    File attch = new File(attachment.getUrl());
                    attch.delete();
                }
            }

            messageEvent.setDataEntry(null);
            log.info("Entry '{}' for message event '{}' was deleted.", eventId, messageEvent.getId());
        }

        schedular.stopTimerService(messageEvent.getExecutor());
        messageEvent.setExecutor(null);
        user.deleteMessageEvent(eventId);

        return new RedirectView("/token/user/csdevent/list", true, false);
    }

    private Token getToken(HttpSession session) {
        Map<Long, Token> tokens = TokenRegistrationController.getTokenDatabase();
        Long tokenId = (Long) session.getAttribute("tokenId");
        Token token = tokens.get(tokenId);

        return token;
    }

    private List<String> getIntervals() {
//        List<String> intervals = new LinkedList<>();
//        intervals.addAll(Arrays.asList(
//                "Week", "Month", "3 Months",
//                "6 Months", "Year"
//        ));

        List<String> intervals = new LinkedList<>();
        for (Map.Entry<String, Integer> entry : MessageEventHelper.getIntervals().entrySet()) {
            intervals.add(entry.getKey());
        }
        return intervals;
    }

    private void uploadFiles(DataEntry entry, EventForm form) throws IOException {
        for (MultipartFile filePart : form.getAttachments()) {
            log.debug("Processing attachment for new entry.");
            Attachment attachment = new Attachment();
            attachment.setName(filePart.getOriginalFilename());
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setContents(filePart.getBytes());

            if ((attachment.getName() != null && attachment.getName().length() > 0)
                    || (attachment.getContents() != null && attachment.getContents().length > 0)) {

                String newFileName = fileUtil.getNewFileName(filePart);
                String storageDirectory = fileUtil.getStorageDirectory();
                File newFile = new File(storageDirectory + newFileName);
                try {
                    filePart.transferTo(newFile);

                    attachment.setId(this.getNextAttachmentId());
                    attachment.setName(filePart.getOriginalFilename());
                    attachment.setNewFileName(newFileName);
                    attachment.setContentType(filePart.getContentType());
                    attachment.setSize(filePart.getSize());
                    attachment.setUrl(newFile.getCanonicalPath());

                    entry.addAttachment(attachment);
                    System.out.println("Attachment was added:" + attachment.getName());
                } catch (IOException | IllegalArgumentException | IllegalStateException e) {
                    log.error("Could not upload file " + filePart.getOriginalFilename(), e);
                }
            }
        }
    }

    public static class EventForm {

        private String subject;
        private String body;
        private List<MultipartFile> attachments;
        private Long[] emailContacts;
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

        public Long[] getEmailContacts() {
            return emailContacts;
        }

        public void setEmailContacts(Long[] emailContacts) {
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
