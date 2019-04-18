package ru.tokens.site.controller;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import ru.tokens.site.entities.MedicalFormEntry;
import ru.tokens.site.entities.MedicalHistory;
import ru.tokens.site.entities.Token;
import ru.tokens.site.entities.User;

/**
 *
 * @author solon4ak
 */
@Controller
@RequestMapping("token/user/med")
public class MedicalHistoryController {

    private static final Logger log = LogManager.getLogger("MedHistFormController");

    @RequestMapping(value = {"view"}, method = RequestMethod.GET)
    public ModelAndView view(Map<String, Object> model, HttpSession session) {

        Long tokenId = (Long) session.getAttribute("tokenId");
        Map<Long, Token> tokens = TokenRegistrationController.getTokenDatabase();
        Token token = tokens.get(tokenId);

        if (token != null && token.isActivated()) {
            model.put("token", token);
            return new ModelAndView("medicaldata/edit/view");
        }

        String message = "Token " + tokenId + "does not exist!";
        model.put("message", message);
        model.put("tokenId", tokenId);
        return new ModelAndView("token/view/error");
    }

    @RequestMapping(value = {"add"}, method = RequestMethod.GET)
    public String create(Map<String, Object> model) {
        model.put("bloodVariants", this.getBloodTypes());
        model.put("medicalForm", new MedicalForm());
        return "medicaldata/edit/add";
    }

    @RequestMapping(value = {"add"}, method = RequestMethod.POST)
    public View create(Map<String, Object> model,
            HttpSession session, MedicalForm form) {

        MedicalHistory medHistory = new MedicalHistory();

        medHistory.setOmsNumber(form.getOmsNumber());
        medHistory.setAllergy(form.getAllergy());
        medHistory.setChildhoodIllness(form.getChildhoodIllness());
        medHistory.setChronicDiseases(form.getChronicDiseases());
        medHistory.setDiseases(form.getDiseases());
        medHistory.setInheritedDiseases(form.getInheritedDiseases());
        medHistory.setInjuries(form.getInjuries());
        medHistory.setSurgicalOperations(form.getSurgicalOperations());
        medHistory.setBloodType(form.getBloodType());
        medHistory.setOrganDonor(form.isIsOrganDonor());

        Long tokenId = (Long) session.getAttribute("tokenId");
        Map<Long, Token> tokens = TokenRegistrationController.getTokenDatabase();
        Token token = tokens.get(tokenId);

        if (token != null) {
            User user = token.getUser();
            if (user != null) {
                user.setMedicalHistory(medHistory);
                model.put("token", token);
                log.info("Medical history for token '{}' has been added", tokenId);
                return new RedirectView("/token/user/med/view", true, false);
            }
        }

        log.info("Medical history for token '{}' hasn't been added", tokenId);
        String message = "Token " + tokenId + "does not exist!";
        model.put("message", message);
        model.put("tokenId", tokenId);
        return new RedirectView("/token/view/error", true, false);
    }

    @RequestMapping(value = {"edit"}, method = RequestMethod.GET)
    public String edit(Map<String, Object> model, HttpSession session) {

        Long tokenId = (Long) session.getAttribute("tokenId");
        Map<Long, Token> tokens = TokenRegistrationController.getTokenDatabase();
        Token token = tokens.get(tokenId);
        if (token != null) {
            User user = token.getUser();
            if (user != null) {
                MedicalHistory medicalHistory = user.getMedicalHistory();

                MedicalForm form = new MedicalForm();
                form.setOmsNumber(medicalHistory.getOmsNumber());
                form.setAllergy(medicalHistory.getAllergy());
                form.setChildhoodIllness(medicalHistory.getChildhoodIllness());
                form.setChronicDiseases(medicalHistory.getChronicDiseases());
                form.setDiseases(medicalHistory.getDiseases());
                form.setInheritedDiseases(medicalHistory.getInheritedDiseases());
                form.setInjuries(medicalHistory.getInjuries());
                form.setSurgicalOperations(medicalHistory.getSurgicalOperations());
                form.setBloodType(medicalHistory.getBloodType());
                form.setIsOrganDonor(medicalHistory.isOrganDonor());

                model.put("bloodVariants", this.getBloodTypes());
                model.put("medicalForm", form);
                model.put("token", token);
                return "medicaldata/edit/edit";
            }
        }

        String message = "Token " + tokenId + "does not exist!";
        model.put("message", message);
        model.put("tokenId", tokenId);
        return "token/view/error";
    }

    @RequestMapping(value = {"edit"}, method = RequestMethod.POST)
    public View edit(Map<String, Object> model,
            HttpSession session, MedicalForm form) {

        Long tokenId = (Long) session.getAttribute("tokenId");
        Map<Long, Token> tokens = TokenRegistrationController.getTokenDatabase();
        Token token = tokens.get(tokenId);
        if (token != null) {
            User user = token.getUser();
            if (user != null) {
                MedicalHistory medicalHistory = user.getMedicalHistory();

                medicalHistory.setOmsNumber(form.getOmsNumber());
                medicalHistory.setAllergy(form.getAllergy());
                medicalHistory.setChildhoodIllness(form.getChildhoodIllness());
                medicalHistory.setChronicDiseases(form.getChronicDiseases());
                medicalHistory.setDiseases(form.getDiseases());
                medicalHistory.setInheritedDiseases(form.getInheritedDiseases());
                medicalHistory.setInjuries(form.getInjuries());
                medicalHistory.setSurgicalOperations(form.getSurgicalOperations());
                medicalHistory.setBloodType(form.getBloodType());
                medicalHistory.setOrganDonor(form.isIsOrganDonor());

                return new RedirectView("/token/user/med/view", true, false);
            }
        }

        log.info("Medical history for token '{}' hasn't been edited", tokenId);
        String message = "Token " + tokenId + "does not exist!";
        model.put("message", message);
        model.put("tokenId", tokenId);
        return new RedirectView("/token/view/error", true, false);
    }
    
    private List<String> getBloodTypes() {
        List<String> types = new LinkedList<>();
        types.addAll(Arrays.asList(
                "0(I) Rh-", "0(I) Rh+",
                "A(II) Rh-", "A(II) Rh+",
                "B(III) Rh-", "B(III) Rh+",
                "AB(IV) Rh-", "AB(IV) Rh+"
        ));
        return types;
    }

    public static class MedicalForm {

        private String omsNumber;
        private String bloodType;
        private boolean isOrganDonor;
        private String childhoodIllness;
        private String diseases;
        private String chronicDiseases;
        private String surgicalOperations;
        private String injuries;
        private String allergy;
        private String inheritedDiseases;
        private Map<Long, MedicalFormEntry> medicalEntries;

        public String getOmsNumber() {
            return omsNumber;
        }

        public void setOmsNumber(String omsNumber) {
            this.omsNumber = omsNumber;
        }

        public String getChildhoodIllness() {
            return childhoodIllness;
        }

        public void setChildhoodIllness(String childhoodIllness) {
            this.childhoodIllness = childhoodIllness;
        }

        public String getDiseases() {
            return diseases;
        }

        public void setDiseases(String diseases) {
            this.diseases = diseases;
        }

        public String getChronicDiseases() {
            return chronicDiseases;
        }

        public void setChronicDiseases(String chronicDiseases) {
            this.chronicDiseases = chronicDiseases;
        }

        public String getSurgicalOperations() {
            return surgicalOperations;
        }

        public void setSurgicalOperations(String surgicalOperations) {
            this.surgicalOperations = surgicalOperations;
        }

        public String getInjuries() {
            return injuries;
        }

        public void setInjuries(String injuries) {
            this.injuries = injuries;
        }

        public String getAllergy() {
            return allergy;
        }

        public void setAllergy(String allergy) {
            this.allergy = allergy;
        }

        public String getInheritedDiseases() {
            return inheritedDiseases;
        }

        public void setInheritedDiseases(String inheritedDiseases) {
            this.inheritedDiseases = inheritedDiseases;
        }

        public String getBloodType() {
            return bloodType;
        }

        public void setBloodType(String bloodType) {
            this.bloodType = bloodType;
        }

        public boolean isIsOrganDonor() {
            return isOrganDonor;
        }

        public void setIsOrganDonor(boolean isOrganDonor) {
            this.isOrganDonor = isOrganDonor;
        }

        public Map<Long, MedicalFormEntry> getMedicalEntries() {
            return medicalEntries;
        }

        public void setMedicalEntries(Map<Long, MedicalFormEntry> medicalEntries) {
            this.medicalEntries = medicalEntries;
        }
    }
}
