package ru.tokens.site.controller;

import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import ru.tokens.site.entities.Address;
import ru.tokens.site.entities.Token;
import ru.tokens.site.entities.User;

/**
 *
 * @author solon4ak
 */
@Controller
@RequestMapping("token/user/address")
public class AddressController {

    private static final Logger log = LogManager.getLogger("Address");

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String addAddress(Map<String, Object> model) {
        model.put("addressForm", new AddressForm());
        return "address/edit/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public View addAddress(HttpSession session, AddressForm form) {
        Address address = new Address();
        address.setCountry(form.getCountry());
        address.setCity(form.getCity());
        address.setStreet(form.getStreet());
        address.setBuilding(form.getBuilding());
        address.setApartment(form.getApartment());

        Long tokenId = (Long) session.getAttribute("tokenId");
        Token token = TokenRegistrationController.getTokenDatabase().get(tokenId);
        User user = token.getUser();
        user.setAddress(address);

        log.info("Address for token '{}' was added", tokenId);
        return new RedirectView("/token/user/view", true, false);
    }

    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String editAddress(Map<String, Object> model, HttpSession session) {
        Long tokenId = (Long) session.getAttribute("tokenId");
        Token token = TokenRegistrationController.getTokenDatabase().get(tokenId);
        User user = token.getUser();

        AddressForm form = new AddressForm();
        form.setCountry(user.getAddress().getCountry());
        form.setCity(user.getAddress().getCity());
        form.setStreet(user.getAddress().getStreet());
        form.setBuilding(user.getAddress().getBuilding());
        form.setApartment(user.getAddress().getApartment());
        model.put("addressForm", form);

        return "address/edit/edit";
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public View editAddress(HttpSession session, AddressForm form) {
        
        Long tokenId = (Long) session.getAttribute("tokenId");
        Token token = TokenRegistrationController.getTokenDatabase().get(tokenId);
        
        User user = token.getUser();
        Address address = user.getAddress();

        address.setCountry(form.getCountry());
        address.setCity(form.getCity());
        address.setStreet(form.getStreet());
        address.setBuilding(form.getBuilding());
        address.setApartment(form.getApartment());

        log.info("Address for token '{}' has been edited", tokenId);
        return new RedirectView("/token/user/view", true, false);
    }

    public static class AddressForm {

        private String country;
        private String city;
        private String street;
        private String building;
        private String apartment;

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

    }
}
