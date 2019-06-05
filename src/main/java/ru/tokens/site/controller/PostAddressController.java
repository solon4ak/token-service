package ru.tokens.site.controller;

import java.security.Principal;
import java.util.Map;
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
import ru.tokens.site.entities.User;
import ru.tokens.site.services.UserService;

/**
 *
 * @author solon4ak
 */
@Controller
@RequestMapping("token/user/postaddress")
public class PostAddressController {
    
    @Autowired
    private UserService userService;

    private static final Logger log = LogManager.getLogger("PostAddressController");

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public ModelAndView addAddress(Map<String, Object> model, Principal principal) {
        
        Long userId = Long.valueOf(principal.getName());
        User user = userService.findUserById(userId);
        
        model.put("addressForm", new PostAddressForm());
        model.put("user", user);
        return new ModelAndView("userreg/postaddr/add");
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public View addAddress(Principal principal, PostAddressForm form) {
        
        Address address = new Address();
        
        address.setCountry(form.getCountry());
        address.setCity(form.getCity());
        address.setStreet(form.getStreet());
        address.setBuilding(form.getBuilding());
        address.setApartment(form.getApartment());
        address.setRegion(form.getRegion());
        address.setZipCode(form.getZipCode());

        Long userId = Long.valueOf(principal.getName());
        User user = userService.findUserById(userId);

        user.setPostAddress(address);

        log.info("Address for user '{}' was added", user.toString());
        return new RedirectView("/token/user/postaddress/view", true, false);
    }
    
    @RequestMapping(value = "view", method = RequestMethod.GET)
    public String viewAddress(Map<String, Object> model, Principal principal) {
        
        Long userId = Long.valueOf(principal.getName());
        User user = userService.findUserById(userId);
        
        Address address = user.getPostAddress();
        model.put("address", address);
        model.put("user", user);
        return "userreg/postaddr/view";
    }

    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public ModelAndView editAddress(Map<String, Object> model, Principal principal) {

        Long userId = Long.valueOf(principal.getName());
        User user = userService.findUserById(userId);
        
        PostAddressForm form = new PostAddressForm();
        
        form.setCountry(user.getPostAddress().getCountry());
        form.setCity(user.getPostAddress().getCity());
        form.setStreet(user.getPostAddress().getStreet());
        form.setBuilding(user.getPostAddress().getBuilding());
        form.setApartment(user.getPostAddress().getApartment());
        form.setRegion(user.getPostAddress().getRegion());
        form.setZipCode(user.getPostAddress().getZipCode());
        
        model.put("user", user);
        model.put("addressForm", form);

        return new ModelAndView("userreg/postaddr/edit");
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public View editAddress(Principal principal, PostAddressForm form) {

        Long userId = Long.valueOf(principal.getName());
        User user = userService.findUserById(userId);
       
        Address address = user.getPostAddress();

        address.setCountry(form.getCountry());
        address.setCity(form.getCity());
        address.setStreet(form.getStreet());
        address.setBuilding(form.getBuilding());
        address.setApartment(form.getApartment());
        address.setRegion(form.getRegion());
        address.setZipCode(form.getZipCode());

        log.info("Address for user '{}' has been edited", user.toString());
        return new RedirectView("/token/user/postaddress/view", true, false);
    }

    public static class PostAddressForm {

        private String country;
        private String region;
        private String city;
        private String street;
        private String building;
        private String apartment;
        private String zipCode;

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

        public String getZipCode() {
            return zipCode;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }
    }
}
