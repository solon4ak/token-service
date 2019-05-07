package ru.tokens.site.controller;

import java.io.File;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ru.tokens.site.entities.Attachment;
import ru.tokens.site.entities.DataEntry;
import ru.tokens.site.entities.MedicalHistory;
import ru.tokens.site.entities.MessageEvent;
import ru.tokens.site.entities.User;
import ru.tokens.site.services.MessageEventService;
import ru.tokens.site.services.UserService;
import ru.tokens.site.services.FileUtil;

/**
 *
 * @author solon4ak
 */
@Controller
public class AttachmentController {

    @Autowired
    private UserService userService;

//    @Autowired
//    @Qualifier("fileService")
//    private FileUtil fileUtil;

    @Autowired
    private MessageEventService eventService;
    
    private static final Logger log = LogManager.getLogger("AttachmentController");

    @RequestMapping(value = "token/user/med/entry/{entryId}/{attachmentId}/delete",
            method = RequestMethod.GET)
    public ModelAndView delete(Map<String, Object> model,
            HttpSession session, @PathVariable("entryId") long entryId,
            @PathVariable("attachmentId") Long attachmentId) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return new ModelAndView(new RedirectView("/login", true, false));
        }
        User user = userService.findUserById(userId);

        MedicalHistory history = user.getMedicalHistory();
        DataEntry entry = history.getMedicalFormEntry(entryId);
        if (null != entry) {
            Attachment attachment = entry.getAttachment(attachmentId);

            File atch = new File(attachment.getUrl());
            atch.delete();

            entry.deleteAttachment(attachmentId);

            model.put("entry", entry);
            model.put("user", user);
        }

        return new ModelAndView("entry/edit/view");
    }

    @RequestMapping(value = "token/user/csdevent/{eventId}/{attachmentId}/delete",
            method = RequestMethod.GET)
    public ModelAndView deleteMessageEventAttachment(Map<String, Object> model,
            HttpSession session, @PathVariable("eventId") Long eventId,
            @PathVariable("attachmentId") Long attachmentId) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return new ModelAndView(new RedirectView("/login", true, false));
        }
        User user = userService.findUserById(userId);

        MessageEvent event = eventService.findMessageEventById(eventId);
        DataEntry entry = event.getDataEntry();

        if (null != entry) {
            Attachment attachment = entry.getAttachment(attachmentId);
            File atch = new File(attachment.getUrl());
            atch.delete();

            entry.deleteAttachment(attachmentId);

            model.put("entry", entry);
            model.put("user", user);
//                return new ModelAndView("entry/edit");
        }
        
        return new ModelAndView(new RedirectView("/token/user/csdevent/edit/" + eventId, true, false));
    }
    
}
