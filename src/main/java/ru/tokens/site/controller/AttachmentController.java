package ru.tokens.site.controller;

import java.io.File;
import java.security.Principal;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ru.tokens.site.entities.Attachment;
import ru.tokens.site.entities.DataEntry;
import ru.tokens.site.entities.MedicalHistory;
import ru.tokens.site.entities.MessageEvent;
import ru.tokens.site.entities.User;
import ru.tokens.site.services.AttachmentService;
import ru.tokens.site.services.MessageEventService;
import ru.tokens.site.services.UserService;

/**
 *
 * @author solon4ak
 */
@Controller
public class AttachmentController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageEventService eventService;
    
    @Autowired
    private AttachmentService attachmentService;
    
    private static final Logger log = LogManager.getLogger("AttachmentController");

    @RequestMapping(value = "token/user/med/entry/{entryId}/{attachmentId}/delete",
            method = RequestMethod.GET)
    public ModelAndView delete(Map<String, Object> model,
            Principal principal, @PathVariable("entryId") long entryId,
            @PathVariable("attachmentId") Long attachmentId) {

        Long userId = Long.valueOf(principal.getName());
        User user = userService.findUserById(userId);

        MedicalHistory history = user.getMedicalHistory();
        DataEntry entry = history.getMedicalFormEntry(entryId);
        if (null != entry) {
            Attachment attachment = entry.getAttachment(attachmentId);

            File atch = new File(attachment.getUrl());
            atch.delete();

            entry.deleteAttachment(attachmentId);
            attachmentService.delete(attachmentId);

            model.put("entry", entry);
            model.put("user", user);
        }

        return new ModelAndView(new RedirectView("/token/user/med/entry/" + entryId + "/edit", true, false));
    }

    @RequestMapping(value = "token/user/csdevent/{eventId}/{attachmentId}/delete",
            method = RequestMethod.GET)
    public ModelAndView deleteMessageEventAttachment(Map<String, Object> model,
            Principal principal, @PathVariable("eventId") Long eventId,
            @PathVariable("attachmentId") Long attachmentId) {

        Long userId = Long.valueOf(principal.getName());
        User user = userService.findUserById(userId);

        MessageEvent event = eventService.findMessageEventById(eventId);
        DataEntry entry = event.getDataEntry();

        if (null != entry) {
            Attachment attachment = entry.getAttachment(attachmentId);
            File atch = new File(attachment.getUrl());
            atch.delete();

            entry.deleteAttachment(attachmentId);
            attachmentService.delete(attachmentId);

            model.put("entry", entry);
            model.put("user", user);
//                return new ModelAndView("entry/edit");
        }
        
        return new ModelAndView(new RedirectView("/token/user/csdevent/edit/" + eventId, true, false));
    }
    
}
