package ru.tokens.site.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author solon4ak
 */
public class MedicalHistory implements Serializable {

    private String omsNumber;
    
    // Группа крови
    private String bloodType;
    
    // Разрешение на изъятие органов
    private boolean organDonor = false;

    // Перенесенные заболевания в детстве (в том числе детские инфекции)
    private String childhoodIllness;

    // Перенесенные заболевания в течении жизни (в том числе туберкулёз 
    // и контакт с ним, сахарный диабет, болезнь Боткина, венерические 
    // заболевания – гонорея, сифилис, СПИД)
    private String diseases;

    // Хронические заболевания
    private String chronicDiseases;

    // Операции
    private String surgicalOperations;

    // Травмы, ранения, контузии
    private String injuries;

    // Аллергические реакции (лекарственные средства, пищевая аллергия, сезонная аллергия)
    private String allergy;

    // Наследственные заболевания
    private String inheritedDiseases;

    // Список перенесенных заболеваний по датам
    private Map<Long, MedicalFormEntry> entries = new LinkedHashMap<>();

    public MedicalHistory() {
    }

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

    public MedicalFormEntry getMedicalFormEntry(Long id) {
        return this.entries.get(id);
    }

    public Collection getMedicalFormEntries() {
        return this.entries.values();
    }

    public void addMedicalFormEntry(MedicalFormEntry entry) {
        this.entries.put(entry.getId(), entry);
    }
    
    public int getNumberOfMedicalFormEntries() {
        return this.entries.size();
    }
    
    public void deleteMedicalFormEntry(long entryId) {        
        this.entries.remove(entryId);
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public boolean isOrganDonor() {
        return organDonor;
    }

    public void setOrganDonor(boolean organDonor) {
        this.organDonor = organDonor;
    }
    
    
}
