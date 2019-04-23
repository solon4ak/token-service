package ru.tokens.site.controller;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
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
import ru.tokens.site.entities.DataEntry;
import ru.tokens.site.entities.MedicalHistory;
import ru.tokens.site.entities.Token;
import ru.tokens.site.entities.User;
import ru.tokens.site.utils.FileUtil;

/**
 *
 * @author solon4ak
 */
@Controller
@RequestMapping("token")
public class EntryController {

    private static final Logger log = LogManager.getLogger("EntryController");

    @Autowired
    @Qualifier("fileService")
    private FileUtil fileUtil;

    private volatile long ENTRY_ID_SEQUENCE = 1;

    private synchronized long getNextEntryId() {
        return this.ENTRY_ID_SEQUENCE++;
    }

    private volatile long ATTACHMENT_ID_SEQUENCE = 1;

    private synchronized long getNextAttachmentId() {
        return this.ATTACHMENT_ID_SEQUENCE++;
    }

    @RequestMapping(
            value = {"{tokenId}/{uuidString}/med/entry/view/{entryId}"},
            method = RequestMethod.GET
    )
    public ModelAndView view(Map<String, Object> model, HttpSession session,
            @PathVariable("tokenId") long tokenId,
            @PathVariable("uuidString") String uuidString,
            @PathVariable("entryId") long entryId) {

        Map<Long, Token> tokens = TokenRegistrationController.getTokenDatabase();
        Token token = tokens.get(tokenId);

        if (token != null && token.isActivated()) {
            User user = token.getUser();
            if (user != null) {
                DataEntry entry = user.getMedicalHistory().getMedicalFormEntry(entryId);
                if (entry != null) {
                    model.put("token", token);
                    model.put("entry", entry);
                    return new ModelAndView("entry/view/view");
                }
            }
        }

        model.put("token", token);
        return this.getListRedirectModelAndView(token);
    }

    @RequestMapping(
            value = "{tokenId}/{uuidString}/med/entry/view/{entryId}/attachment/{attachment:.+}",
            method = RequestMethod.GET
    )
    public View download(
            @PathVariable("tokenId") long tokenId,
            @PathVariable("uuidString") String uuidString,
            @PathVariable("entryId") long entryId,
            @PathVariable("attachment") String name) {

        Map<Long, Token> tokens = TokenRegistrationController.getTokenDatabase();
        Token token = tokens.get(tokenId);

        if (token != null && token.isActivated()) {
            User user = token.getUser();
            if (user != null) {
                DataEntry entry = user.getMedicalHistory().getMedicalFormEntry(entryId);
                if (entry != null) {
                    Attachment attachment = entry.getAttachment(name);
                    if (attachment == null) {
                        log.info("Requested attachment {} not found on entry {}.", name, entry);
                        return this.getListRedirectView(token);
                    }
                    return new DownloadingView(
                            attachment.getName(),
                            attachment.getMimeContentType(),
                            attachment.getContents());
                }
            }
        }
        return this.getListRedirectView(token);
    }

    @RequestMapping(value = "user/med/entry/{entryId}/view", method = RequestMethod.GET)
    public String viewEntry(Map<String, Object> model, HttpSession session,
            @PathVariable("entryId") long entryId) {
        Map<Long, Token> tokens = TokenRegistrationController.getTokenDatabase();
        Long tokenId = (Long) session.getAttribute("tokenId");
        Token token = tokens.get(tokenId);
        User user = token.getUser();
        MedicalHistory history = user.getMedicalHistory();
        DataEntry entry = history.getMedicalFormEntry(entryId);

        model.put("entry", entry);
        model.put("token", token);
        return "entry/edit/view";
    }

    @RequestMapping(value = "user/med/entry/create", method = RequestMethod.GET)
    public String create(Map<String, Object> model, HttpSession session) {

        Map<Long, Token> tokens = TokenRegistrationController.getTokenDatabase();
        Long tokenId = (Long) session.getAttribute("tokenId");
        Token token = tokens.get(tokenId);

        model.put("token", token);
        model.put("entryForm", new EntryForm());
        return "entry/edit/add";
    }

    @RequestMapping(value = "user/med/entry/create", method = RequestMethod.POST)
    public View create(HttpSession session, EntryForm form) throws IOException {

        Map<Long, Token> tokens = TokenRegistrationController.getTokenDatabase();
        Long tokenId = (Long) session.getAttribute("tokenId");
        Token token = tokens.get(tokenId);

        if (null == token) {
            return new RedirectView("/login", true, false);
        }

        User user = token.getUser();

        DataEntry entry = new DataEntry();
        entry.setId(this.getNextEntryId());
        String subject = form.getSubject();
        if (subject.isEmpty() || subject.equals("")) {
            subject = "new";
        }
        entry.setSubject(subject);
        entry.setBody(form.getBody());
        entry.setDateCreated(Instant.now());
        
        this.processAttachment(entry, form);

        user.getMedicalHistory().addMedicalFormEntry(entry);
        return new RedirectView("/token/user/med/view", true, false);
    }

    @RequestMapping(value = "user/med/entry/{entryId}/delete", method = RequestMethod.GET)
    public View delete(HttpSession session, @PathVariable("entryId") long entryId) {

        Map<Long, Token> tokens = TokenRegistrationController.getTokenDatabase();
        Long tokenId = (Long) session.getAttribute("tokenId");
        Token token = tokens.get(tokenId);

        if (null == token) {
            return new RedirectView("/login", true, false);
        }

        User user = token.getUser();

        if (null != user) {
            MedicalHistory medicalHistory = user.getMedicalHistory();
            DataEntry entry = medicalHistory.getMedicalFormEntry(entryId);
            if (null != entry) {

                Map<Long, Attachment> attachments = entry.getAttachmentsMap();
                if (!attachments.isEmpty()) {
                    String path = fileUtil.getStorageDirectory();
                    for (Map.Entry<Long, Attachment> a : attachments.entrySet()) {
                        Attachment attachment = a.getValue();
                        File atch = new File(attachment.getUrl());
                        atch.delete();
                    }
                }

                medicalHistory.deleteMedicalFormEntry(entryId);
                log.info("Entry '{}' for token '{}' was deleted.", entryId, tokenId);
            }
        }
        return new RedirectView("/token/user/med/view", true, false);
    }

    @RequestMapping(value = "user/med/entry/{entryId}/edit", method = RequestMethod.GET)
    public ModelAndView edit(Map<String, Object> model, HttpSession session,
            @PathVariable("entryId") long entryId) {

        Map<Long, Token> tokens = TokenRegistrationController.getTokenDatabase();
        Long tokenId = (Long) session.getAttribute("tokenId");
        Token token = tokens.get(tokenId);

        if (null == token) {
            return new ModelAndView(new RedirectView("/login", true, false));
        }

        User user = token.getUser();
        EntryForm form = new EntryForm();

        if (null != user) {
            MedicalHistory medicalHistory = user.getMedicalHistory();
            DataEntry entry = medicalHistory.getMedicalFormEntry(entryId);
            if (null != entry) {
                form.setSubject(entry.getSubject());
                form.setBody(entry.getBody());
                model.put("entry", entry);
                model.put("user", user);
                model.put("token", token);
                model.put("entryForm", form);
                return new ModelAndView("entry/edit/edit");
            }
        }
        return new ModelAndView(new RedirectView("/token/user/med/view", true, false));
    }

    @RequestMapping(value = "user/med/entry/{entryId}/edit", method = RequestMethod.POST)
    public View edit(HttpSession session, EntryForm form,
            @PathVariable("entryId") long entryId) throws IOException {

        Map<Long, Token> tokens = TokenRegistrationController.getTokenDatabase();
        Long tokenId = (Long) session.getAttribute("tokenId");
        Token token = tokens.get(tokenId);

        if (null == token) {
            return new RedirectView("/login", true, false);
        }

        User user = token.getUser();

        if (null != user) {
            MedicalHistory medicalHistory = user.getMedicalHistory();
            DataEntry entry = medicalHistory.getMedicalFormEntry(entryId);
            if (null != entry) {
                entry.setSubject(form.getSubject());
                entry.setBody(form.getBody());
                
                this.processAttachment(entry, form);
                
                log.info("Editing entry for token '{}'.", tokenId);
                // user/med/entry/{entryId}/view
                return new RedirectView("/token/user/med/entry/" + entry.getId() + "/view", true, false);
            }
        }
        return new RedirectView("/token/user/med/view", true, false);
    }

    private ModelAndView getListRedirectModelAndView(Token token) {
        return new ModelAndView(this.getListRedirectView(token));
    }

    private View getListRedirectView(Token token) {
        return new RedirectView(
                "/token/" + token.getTokenId() + "/" + token.getUuidString(), true, false
        );
    }

    private void processAttachment(DataEntry entry, EntryForm form) 
            throws IOException {
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
                } catch (IOException
                        | IllegalArgumentException | IllegalStateException e) {
                    log.error("Could not upload file " + filePart.getOriginalFilename(), e);
                }
            }
        }
    }

    public static class EntryForm {

        private String subject;
        private String body;
        private List<MultipartFile> attachments;

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
    }

}
