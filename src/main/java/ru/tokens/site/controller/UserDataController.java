package ru.tokens.site.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ru.tokens.site.entities.Token;
import ru.tokens.site.entities.User;
import ru.tokens.site.services.TokenService;
import ru.tokens.site.services.UserService;
import ru.tokens.site.utils.TimeUtils;

/**
 *
 * @author solon4ak
 */
@Controller
@RequestMapping("token")
public class UserDataController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    private static final Logger log = LogManager.getLogger("UserDataController");

    @RequestMapping(value = "{tokenId}/{uuidString}", method = RequestMethod.GET)
    public ModelAndView view(Map<String, Object> model,
            @PathVariable("tokenId") Long tokenId,
            @PathVariable("uuidString") String uuidString,
            @RequestParam("showMH") boolean showMH) {

        String msg;

        if (tokenId == null || uuidString == null) {
            msg = "Такой жетон не существует";
            model.put("message", msg);
            model.put("uuidString", uuidString);
            return new ModelAndView("token/view/error");
        }

        Token token = tokenService.findTokenById(tokenId);

        if (token == null || !token.isActivated()
                || !uuidString.equals(token.getUuidString())) {
            msg = "Жетон не существует или не активирован";
            model.put("message", msg);
            model.put("uuidString", uuidString);
            return new ModelAndView("token/view/error");
        }

        User user = token.getUser();
        LocalDate birthdate = user.getBirthDate();
        int age = TimeUtils.getYearsPassedFromDate(birthdate);

        model.put("token", token);
        model.put("age", age);
        model.put("user", user);

        System.out.println("--- goes to view page for outer context for tokenId " + tokenId);
        if (showMH) {
            return new ModelAndView("user/view/view");
        }
        return new ModelAndView("user/view/address");
    }

    @RequestMapping(value = "user/view", method = RequestMethod.GET)
    public ModelAndView view(Map<String, Object> model, Principal principal) {

        Long userId = Long.valueOf(principal.getName());
        User user = userService.findUserById(userId);

        Token token = tokenService.findTokenByUser(user);
        if (token == null || !token.isActivated()) {
            return new ModelAndView(new RedirectView("/token/register", true, false));
        }

        model.put("token", token);
        model.put("user", user);
        System.out.println("--- goes to view page for registered user with tokenId " + token.getTokenId());
        return new ModelAndView("user/edit/view");
    }

    @RequestMapping(value = "user/picture/view", method = RequestMethod.GET)
    public ModelAndView getPicture(Map<String, Object> model, Principal principal) {

        Long userId = Long.valueOf(principal.getName());
        User user = userService.findUserById(userId);

        Token token = tokenService.findTokenByUser(user);
        if (token == null || !token.isActivated()) {
            return new ModelAndView(new RedirectView("/token/register", true, false));
        }

        model.put("token", token);
        model.put("user", user);
        return new ModelAndView("image/edit/view");
    }
    
}
