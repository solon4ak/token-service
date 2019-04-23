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
import ru.tokens.site.entities.Token;
import ru.tokens.site.entities.User;
import ru.tokens.site.utils.FileUtil;

/**
 *
 * @author solon4ak
 */
@Controller
public class AttachmentController {

    private static final Logger log = LogManager.getLogger("AttachmentController");

    @Autowired
    @Qualifier("fileService")
    private FileUtil fileUtil;

    @RequestMapping(value = "token/user/med/entry/{entryId}/{attachmentId}/delete",
            method = RequestMethod.GET)
    public ModelAndView delete(Map<String, Object> model,
            HttpSession session, @PathVariable("entryId") long entryId,
            @PathVariable("attachmentId") Long attachmentId) {

        Map<Long, Token> tokens = TokenRegistrationController.getTokenDatabase();
        Long tokenId = (Long) session.getAttribute("tokenId");
        Token token = tokens.get(tokenId);

        if (null == token) {
            return new ModelAndView(new RedirectView("/login", true, false));
        }

        User user = token.getUser();

        if (null != user) {
            MedicalHistory history = user.getMedicalHistory();
            DataEntry entry = history.getMedicalFormEntry(entryId);
            if (null != entry) {
                Attachment attachment = entry.getAttachment(attachmentId);

                File atch = new File(attachment.getUrl());
                atch.delete();

                entry.deleteAttachment(attachmentId);

                model.put("entry", entry);
                model.put("user", user);
//                return new ModelAndView("entry/edit");
            }
        }
        return new ModelAndView("entry/edit/view");
    }
    
    @RequestMapping(value = "token/user/csdevent/{eventId}/{attachmentId}/delete",
            method = RequestMethod.GET)
    public ModelAndView deleteMessageEventAttachment(Map<String, Object> model,
            HttpSession session, @PathVariable("eventId") Long eventId,
            @PathVariable("attachmentId") Long attachmentId) {

        Map<Long, Token> tokens = TokenRegistrationController.getTokenDatabase();
        Long tokenId = (Long) session.getAttribute("tokenId");
        Token token = tokens.get(tokenId);

        if (null == token) {
            return new ModelAndView(new RedirectView("/login", true, false));
        }

        User user = token.getUser();

        if (null != user) {
            MessageEvent event = user.getMessageEvent(eventId);
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
        }
        return new ModelAndView(new RedirectView("/token/user/csdevent/edit/" + eventId, true, false));
    }
}
