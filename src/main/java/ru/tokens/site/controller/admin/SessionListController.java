package ru.tokens.site.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
@RequestMapping("session")
public class SessionListController {
    
    @Autowired
    private SessionRegistry sessionRegistry;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(Map<String, Object> model) {
        model.put("timestamp", System.currentTimeMillis());
        model.put("numberOfSessions", this.sessionRegistry.getNumberOfSessions());
        model.put("sessionList", this.sessionRegistry.getAllSessions());

        return "session/list";
    }
}
