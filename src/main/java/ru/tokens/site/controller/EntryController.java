package ru.tokens.site.controller;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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
import ru.tokens.site.services.AttachmentService;
import ru.tokens.site.services.TokenService;
import ru.tokens.site.services.UserService;
import ru.tokens.site.services.FileUtil;
import ru.tokens.site.services.DataEntryService;

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

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private DataEntryService dataEntryService;

    @Autowired
    private AttachmentService attachmentService;

    @RequestMapping(
            value = {"{tokenId}/{uuidString}/med/entry/view/{entryId}"},
            method = RequestMethod.GET
    )
    public ModelAndView view(final Map<String, Object> model,
            final @PathVariable("tokenId") long tokenId,
            final @PathVariable("uuidString") String uuidString,
            final @PathVariable("entryId") long entryId) {

        final Token token = tokenService.findTokenById(tokenId);

        if (token != null && token.isActivated()) {
            final User user = token.getUser();
            if (user != null) {
                final DataEntry entry = user.getMedicalHistory().getMedicalFormEntry(entryId);
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
            final @PathVariable("tokenId") long tokenId,
            final @PathVariable("uuidString") String uuidString,
            final @PathVariable("entryId") long entryId,
            final @PathVariable("attachment") String name) {

        final Token token = tokenService.findTokenById(tokenId);

        if (token != null && token.isActivated()) {
            final User user = token.getUser();
            if (user != null) {
                final DataEntry entry = user.getMedicalHistory().getMedicalFormEntry(entryId);
                if (entry != null) {
                    final Attachment attachment = entry.getAttachment(name);
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
    public ModelAndView viewEntry(final Map<String, Object> model, final Principal principal,
            final @PathVariable("entryId") long entryId) {

        final Long userId = Long.valueOf(principal.getName());
        final User user = userService.findUserById(userId);

        final Token token = tokenService.findTokenByUser(user);
        if (token == null) {
            return new ModelAndView(new RedirectView("/token/register", true, false));
        }

        final MedicalHistory history = user.getMedicalHistory();
        final DataEntry entry = history.getMedicalFormEntry(entryId);

        model.put("entry", entry);
        model.put("token", token);
        model.put("user", user);
        return new ModelAndView("entry/edit/view");
    }

    @RequestMapping(value = "user/med/entry/create", method = RequestMethod.GET)
    public ModelAndView create(final Map<String, Object> model, final Principal principal) {

        final Long userId = Long.valueOf(principal.getName());
        final User user = userService.findUserById(userId);

        final Token token = tokenService.findTokenByUser(user);
        if (token == null) {
            return new ModelAndView(new RedirectView("/token/register", true, false));
        }

        model.put("token", token);
        model.put("user", user);
        model.put("entryForm", new EntryForm());
        return new ModelAndView("entry/edit/add");
    }

    @RequestMapping(value = "user/med/entry/create", method = RequestMethod.POST)
    public View create(final Principal principal, final EntryForm form) throws IOException {

        final Long userId = Long.valueOf(principal.getName());
        final User user = userService.findUserById(userId);

        final DataEntry entry = new DataEntry();
        String subject = form.getSubject();
        if (subject.isEmpty() || subject.equals("")) {
            subject = "new";
        }
        entry.setSubject(subject);
        entry.setBody(form.getBody());
        entry.setDateCreated(Instant.now());

        this.processAttachment(entry, form);

        dataEntryService.save(entry);
        user.getMedicalHistory().addMedicalFormEntry(entry);
        return new RedirectView("/token/user/med/entry/list", true, false);
    }

    @RequestMapping(value = "user/med/entry/{entryId}/delete", method = RequestMethod.GET)
    public View delete(final Principal principal, final @PathVariable("entryId") long entryId) {

        final Long userId = Long.valueOf(principal.getName());
        final User user = userService.findUserById(userId);

        final Token token = tokenService.findTokenByUser(user);
        if (token == null) {
            return new RedirectView("/token/register", true, false);
        }

        final MedicalHistory medicalHistory = user.getMedicalHistory();
        final DataEntry entry = medicalHistory.getMedicalFormEntry(entryId);
        if (null != entry) {
            final Collection<Attachment> attachments = entry.getAttachments();
            if (!attachments.isEmpty()) {
                for (Attachment a : attachments) {
                    final File atch = new File(a.getUrl());
                    atch.delete();
//                    entry.getAttachmentsMap().remove(a.getId());
                    attachmentService.delete(a.getId());
                }
            }

            medicalHistory.deleteMedicalFormEntry(entryId);
            dataEntryService.delete(entryId);
            log.info("Entry '{}' for token '{}' was deleted.", entryId, token.getTokenId());
        }

        return new RedirectView("/token/user/med/entry/list", true, false);
    }

    @RequestMapping(value = "user/med/entry/{entryId}/edit", method = RequestMethod.GET)
    public ModelAndView edit(final Map<String, Object> model, final Principal principal,
            final @PathVariable("entryId") long entryId) {

        final Long userId = Long.valueOf(principal.getName());
        final User user = userService.findUserById(userId);

        final Token token = tokenService.findTokenByUser(user);
        if (token == null) {
            return new ModelAndView(new RedirectView("/token/register", true, false));
        }

        final EntryForm form = new EntryForm();

        final MedicalHistory medicalHistory = user.getMedicalHistory();
        final DataEntry entry = medicalHistory.getMedicalFormEntry(entryId);
        if (null != entry) {
            form.setSubject(entry.getSubject());
            form.setBody(entry.getBody());
            model.put("entry", entry);
            model.put("user", user);
            model.put("token", token);
            model.put("entryForm", form);
            return new ModelAndView("entry/edit/edit");
        }

        return new ModelAndView(new RedirectView("/token/user/med/view", true, false));
    }

    @RequestMapping(value = "user/med/entry/{entryId}/edit", method = RequestMethod.POST)
    public View edit(final Principal principal, final EntryForm form,
            final @PathVariable("entryId") long entryId) throws IOException {

        final Long userId = Long.valueOf(principal.getName());
        final User user = userService.findUserById(userId);

        final Token token = tokenService.findTokenByUser(user);
        if (token == null) {
            return new RedirectView("/token/register", true, false);
        }

        final MedicalHistory medicalHistory = user.getMedicalHistory();
        final DataEntry entry = medicalHistory.getMedicalFormEntry(entryId);
        if (null != entry) {
            entry.setSubject(form.getSubject());
            entry.setBody(form.getBody());

            this.processAttachment(entry, form);

            dataEntryService.save(entry);
            log.info("Editing entry for token '{}'.", token.getTokenId());
            return new RedirectView("/token/user/med/entry/" + entry.getId() + "/view", true, false);
        }

        return new RedirectView("/token/user/med/entry/list", true, false);
    }

    @RequestMapping(value = "user/med/entry/list", method = RequestMethod.GET)
    public ModelAndView listEntries(final Map<String, Object> model, final Principal principal) {

        final Long userId = Long.valueOf(principal.getName());
        final User user = userService.findUserById(userId);

        final Token token = tokenService.findTokenByUser(user);        
        if (token == null || !token.isActivated()) {
            return new ModelAndView(new RedirectView("/token/register", true, false));
        } 
        
        final MedicalHistory medHist = user.getMedicalHistory();
        final Collection<DataEntry> entries = medHist.getMedicalFormEntries();

        model.put("entries", entries);
        model.put("user", user);
        model.put("token", token);
        return new ModelAndView("entry/edit/list");
    }

    private ModelAndView getListRedirectModelAndView(final Token token) {
        return new ModelAndView(this.getListRedirectView(token));
    }

    private View getListRedirectView(final Token token) {
        return new RedirectView(
                "/token/" + token.getTokenId() + "/" + token.getUuidString(), true, false
        );
    }

    private synchronized void processAttachment(final DataEntry entry, final EntryForm form)
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

                    attachment.setName(filePart.getOriginalFilename());
                    attachment.setNewFileName(newFileName);
                    attachment.setContentType(filePart.getContentType());
                    attachment.setSize(filePart.getSize());
                    attachment.setUrl(newFile.getCanonicalPath());

                    attachmentService.save(attachment);
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
