package ru.tokens.site.controller;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
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
import ru.tokens.site.services.MessageEventService;
import ru.tokens.site.services.FileUtil;
import ru.tokens.site.utils.MessageEventHelper;
import ru.tokens.site.services.TimerProlongationLinkService;
import ru.tokens.site.services.TokenService;
import ru.tokens.site.services.UserService;

/**
 *
 * @author solon4ak
 */
@Controller
@RequestMapping("token")
public class MessageEventController {

    private static final Logger log = LogManager.getLogger(MessageEventController.class);

    @Autowired
    @Qualifier("fileService")
    private FileUtil fileUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageEventService eventService;
    
    @Autowired
    private TokenService tokenService;

    @Autowired
    private TimerProlongationLinkService linkService;

    private volatile long ENTRY_ID_SEQUENCE = 100;

    private synchronized long getNextEntryId() {
        return this.ENTRY_ID_SEQUENCE++;
    }

    private volatile long ATTACHMENT_ID_SEQUENCE = 100;

    private synchronized long getNextAttachmentId() {
        return this.ATTACHMENT_ID_SEQUENCE++;
    }

    @RequestMapping(value = "user/csdevent/view/{eventId}", method = RequestMethod.GET)
    public ModelAndView viewMessageEvent(Map<String, Object> model, HttpSession session,
            @PathVariable("eventId") Long eventId) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return new ModelAndView(new RedirectView("/login", true, false));
        }
        User user = userService.findUserById(userId);

        Token token = tokenService.findTokenByUser(user);
        if (token == null || !token.isActivated()) {
            return new ModelAndView(new RedirectView("/token/register", true, false));
        }

        MessageEvent event = eventService.findMessageEventById(eventId);

        model.put("token", token);
        model.put("event", event);
        model.put("user", user);

        return new ModelAndView("causedevent/view");
    }

    @RequestMapping(value = "user/csdevent/add", method = RequestMethod.GET)
    public ModelAndView getCausedEventForm(Map<String, Object> model, HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return new ModelAndView(new RedirectView("/login", true, false));
        }
        User user = userService.findUserById(userId);

        Token token = tokenService.findTokenByUser(user);
        if (token == null || !token.isActivated()) {
            return new ModelAndView(new RedirectView("/token/register", true, false));
        }

        Collection<Contact> contacts = user.getContacts();

        model.put("contacts", contacts);
        model.put("emailIntervals", this.getIntervals());
        model.put("eventForm", new EventForm());
        model.put("user", user);
        return new ModelAndView("causedevent/add");
    }

    @RequestMapping(value = "user/csdevent/add", method = RequestMethod.POST)
    public View getCausedEventForm(Map<String, Object> model,
            HttpSession session, EventForm form) throws IOException {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return new RedirectView("/login", true, false);
        }
        User user = userService.findUserById(userId);

        Token token = tokenService.findTokenByUser(user);
        if (token == null || !token.isActivated()) {
            return new RedirectView("/token/register", true, false);
        }

        MessageEvent messageEvent = new MessageEvent();
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
//        user.addMessageEvent(messageEvent);
        messageEvent.setStatus(MessageEvent.MessageEventStatus.PENDING);
        eventService.addMessageEvent(messageEvent);

        return new RedirectView("/token/user/csdevent/view/" + messageEvent.getId(), true, false);
    }

    @RequestMapping(value = "user/csdevent/list", method = RequestMethod.GET)
    public ModelAndView list(Map<String, Object> model, HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return new ModelAndView(new RedirectView("/login", true, false));
        }
        User user = userService.findUserById(userId);

        Token token = tokenService.findTokenByUser(user);
        if (token == null || !token.isActivated()) {
            return new ModelAndView(new RedirectView("/token/register", true, false));
        }

        Collection<MessageEvent> messageEvents
                = eventService.getMessageEventsForUser(user);

        model.put("token", token);
        model.put("messageEvents", messageEvents);
        model.put("user", user);
        return new ModelAndView("causedevent/list");
    }

    @RequestMapping(value = "user/csdevent/edit/{eventId}", method = RequestMethod.GET)
    public ModelAndView edit(Map<String, Object> model, HttpSession session,
            @PathVariable("eventId") Long eventId) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return new ModelAndView(new RedirectView("/login", true, false));
        }
        User user = userService.findUserById(userId);

        Token token = tokenService.findTokenByUser(user);
        if (token == null || !token.isActivated()) {
            return new ModelAndView(new RedirectView("/token/register", true, false));
        }

        MessageEvent event = eventService.findMessageEventById(eventId);
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
        model.put("user", user);

        return new ModelAndView("causedevent/edit");
    }

    @RequestMapping(value = "user/csdevent/edit/{eventId}", method = RequestMethod.POST)
    public View edit(HttpSession session, EventForm form,
            @PathVariable("eventId") long eventId) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return new RedirectView("/login", true, false);
        }
        User user = userService.findUserById(userId);

        Token token = tokenService.findTokenByUser(user);
        if (token == null || !token.isActivated()) {
            return new RedirectView("/token/register", true, false);
        }

        MessageEvent messageEvent = eventService.findMessageEventById(eventId);
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

        try {
            this.uploadFiles(entry, form);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(MessageEventController.class.getName()).log(Level.SEVERE, null, ex);
        }

        messageEvent.setDataEntry(entry);
//        user.addMessageEvent(messageEvent);
        messageEvent.setStatus(MessageEvent.MessageEventStatus.PENDING);
        eventService.addMessageEvent(messageEvent);

        // run messageEventTask
        return new RedirectView("/token/user/csdevent/view/" + messageEvent.getId(), true, false);
    }

    @RequestMapping(value = "user/csdevent/delete/{eventId}", method = RequestMethod.GET)
    public View delete(HttpSession session, @PathVariable("eventId") Long eventId) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return new RedirectView("/login", true, false);
        }
        User user = userService.findUserById(userId);

        Token token = tokenService.findTokenByUser(user);
        if (token == null || !token.isActivated()) {
            return new RedirectView("/token/register", true, false);
        }

        MessageEvent messageEvent = eventService.findMessageEventById(eventId);
        DataEntry entry = messageEvent.getDataEntry();

        if (null != entry) {
            Map<Long, Attachment> attachments = entry.getAttachmentsMap();
            if (!attachments.isEmpty()) {
                for (Map.Entry<Long, Attachment> a : attachments.entrySet()) {
                    Attachment attachment = a.getValue();
                    File attch = new File(attachment.getUrl());
                    attch.delete();
                }
            }

            messageEvent.setDataEntry(null);
            log.info("Entry '{}' for message event '{}' was deleted.", eventId, messageEvent.getId());
        }

        eventService.deleteMessageEvent(eventId);

        return new RedirectView("/token/user/csdevent/list", true, false);
    }

    @RequestMapping(value = "prolongationConfirm/{eventId}/{tokenString}", method = RequestMethod.GET)
    public View processProlongationLink(HttpSession session,
            @PathVariable("tokenString") String prolongationTokenString,
            @PathVariable("eventId") Long eventId) {

        MessageEvent event = eventService.findMessageEventById(eventId);
        // установили флаг prolonged в MessageEvent
        linkService.checkProlongationToken(event, prolongationTokenString);
        
        if (event.isProlonged()) {
            event.setWaitingProlongation(false);
            log.warn("Prolongtion link was confirmed. "
                    + "Event was prolonged for subject {}", event.getDataEntry().getSubject());
        }

        session.setAttribute("userId", event.getUser().getUserId());
        return new RedirectView("/token/user/csdevent/list", true, false);
    }

    @RequestMapping(value = "user/csdevent/start/{eventId}", method = RequestMethod.GET)
    public View startMessageEvent(@PathVariable("eventId") Long eventId) {
        MessageEvent event = eventService.findMessageEventById(eventId);
        
        if (event.getContacts().isEmpty()) {
            return new RedirectView("/token/user/csdevent/edit/" + event.getId(), true, false);
        }
        
        linkService.startTimerService(event);
        log.warn("Event was started for subject {}", event.getDataEntry().getSubject());

        return new RedirectView("/token/user/csdevent/list", true, false);
    }

    @RequestMapping(value = "user/csdevent/stop/{eventId}", method = RequestMethod.GET)
    public View stopMessageEvent(@PathVariable("eventId") Long eventId) {
        MessageEvent event = eventService.findMessageEventById(eventId);
        linkService.stopTimerService(event);
        log.warn("Event was stopped for subject {}", event.getDataEntry().getSubject());

        return new RedirectView("/token/user/csdevent/list", true, false);
    }

    @RequestMapping(value = "user/csdevent/confirm/{eventId}", method = RequestMethod.GET)
    public View confirmMessageEventUserStatus(@PathVariable("eventId") Long eventId) {
        MessageEvent event = eventService.findMessageEventById(eventId);
        event.setProlonged(true);
        event.setWaitingProlongation(false);
        log.warn("Event was prolonged for subject {}", event.getDataEntry().getSubject());

        return new RedirectView("/token/user/csdevent/list", true, false);
    }

    private List<String> getIntervals() {

        List<String> intervals = new LinkedList<>();
        for (Map.Entry<String, Integer> entry : MessageEventHelper.getIntervals().entrySet()) {
            intervals.add(entry.getKey());
        }
        return intervals;
    }

    private synchronized void uploadFiles(DataEntry entry, EventForm form) throws IOException {
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
