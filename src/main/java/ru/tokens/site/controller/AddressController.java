package ru.tokens.site.controller;

import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import ru.tokens.site.entities.Address;
import ru.tokens.site.entities.Token;
import ru.tokens.site.entities.User;
import ru.tokens.site.services.UserService;

/**
 *
 * @author solon4ak
 */
@Controller
@RequestMapping("token/user/address")
public class AddressController {

    @Autowired
    private UserService userService;

    private static final Logger log = LogManager.getLogger("Address");

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public ModelAndView addAddress(Map<String, Object> model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return new ModelAndView(new RedirectView("/login", true, false));
        }
        User user = userService.findUserById(userId);

        Map<Long, Token> tokens = TokenRegistrationController.getTokenDatabase();

        Token token = tokens.get(user.getToken().getTokenId());
        if (token == null || !token.isActivated()) {
            return new ModelAndView(new RedirectView("/token/register", true, false));
        }
        model.put("addressForm", new AddressForm());
        model.put("token", token);
        model.put("user", user);
        return new ModelAndView("address/edit/add");
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public View addAddress(HttpSession session, AddressForm form) {
        Address address = new Address();
        address.setCountry(form.getCountry());
        address.setCity(form.getCity());
        address.setStreet(form.getStreet());
        address.setBuilding(form.getBuilding());
        address.setApartment(form.getApartment());
        address.setRegion(form.getRegion());

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return new RedirectView("/login", true, false);
        }
        User user = userService.findUserById(userId);

        Map<Long, Token> tokens = TokenRegistrationController.getTokenDatabase();
        Token token = tokens.get(user.getToken().getTokenId());
        if (token == null || !token.isActivated()) {
            return new RedirectView("/token/register", true, false);
        }

        user.setTokenAddress(address);

        log.info("Address for token '{}' was added", token.getTokenId());
        return new RedirectView("/token/user/view", true, false);
    }
    
    @RequestMapping(value = "addpostaddr", method = RequestMethod.GET)
    public View usePostAddress(HttpSession session) {
        
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return new RedirectView("/login", true, false);
        }
        
        User user = userService.findUserById(userId);
        
        Address address = new Address();
        address.setCountry(user.getPostAddress().getCountry());
        address.setCity(user.getPostAddress().getCity());
        address.setStreet(user.getPostAddress().getStreet());
        address.setBuilding(user.getPostAddress().getBuilding());
        address.setApartment(user.getPostAddress().getApartment());
        address.setRegion(user.getPostAddress().getRegion());
        
        user.setTokenAddress(address);
        
        return new RedirectView("/token/user/view", true, false);
    }

    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public ModelAndView editAddress(Map<String, Object> model, HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return new ModelAndView(new RedirectView("/login", true, false));
        }
        
        User user = userService.findUserById(userId);

        Map<Long, Token> tokens = TokenRegistrationController.getTokenDatabase();

        Token token = tokens.get(user.getToken().getTokenId());
        if (token == null || !token.isActivated()) {
            return new ModelAndView(new RedirectView("/token/register", true, false));
        }

        AddressForm form = new AddressForm();
        form.setCountry(user.getTokenAddress().getCountry());
        form.setCity(user.getTokenAddress().getCity());
        form.setStreet(user.getTokenAddress().getStreet());
        form.setBuilding(user.getTokenAddress().getBuilding());
        form.setApartment(user.getTokenAddress().getApartment());
        form.setRegion(user.getTokenAddress().getRegion());
        
        model.put("token", token);
        model.put("user", user);
        model.put("addressForm", form);

        return new ModelAndView("address/edit/edit");
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public View editAddress(HttpSession session, AddressForm form) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return new RedirectView("/login", true, false);
        }
        
        User user = userService.findUserById(userId);

        Map<Long, Token> tokens = TokenRegistrationController.getTokenDatabase();

        Token token = tokens.get(user.getToken().getTokenId());
        if (token == null || !token.isActivated()) {
            return new RedirectView("/token/register", true, false);
        }
        Address address = user.getTokenAddress();

        address.setCountry(form.getCountry());
        address.setCity(form.getCity());
        address.setStreet(form.getStreet());
        address.setBuilding(form.getBuilding());
        address.setApartment(form.getApartment());
        address.setRegion(form.getRegion());

        log.info("Address for token '{}' has been edited", token.getTokenId());
        return new RedirectView("/token/user/view", true, false);
    }

    public static class AddressForm {

        private String country;
        private String city;
        private String street;
        private String building;
        private String apartment;
        private String region;

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getBuilding() {
            return building;
        }

        public void setBuilding(String building) {
            this.building = building;
        }

        public String getApartment() {
            return apartment;
        }

        public void setApartment(String apartment) {
            this.apartment = apartment;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

    }
}
