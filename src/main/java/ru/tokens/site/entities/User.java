package ru.tokens.site.entities;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 * @author solon4ak
 */
public class User {

    /**
     * User logger
     */
    private static final Logger LOGGER = Logger.getLogger("User");

    private String firstName;
    private String lastName;
    private String middleName;
    private String password;
    private String email;
    private Passport passport;
    private BirthCertificate birthCertificate;
    private Address address;
    private String phoneNumber;

    private LocalDate birthDate;
    private Image image;
    
    private boolean emailActivated;

    private final List<Contact> contacts = new LinkedList<>();

    private final Map<Long, Email> emails = new Hashtable<>();

    private MedicalHistory medicalHistory;

    // Список действий, наступление которых обусловлено каким-то событием 
    // со своим индикатором наступления
    private List<CausedEvent> causedEvents;

    private List<ActivationLink> activationLinks = new LinkedList<>();

    public User() {
        super();
        this.emailActivated = false;
    }

    public User(String firstName, String lastName, String email, String password, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthDate = birthDate;
        if (password != null) {
            this.setPassword(password);
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void addContact(Contact contact) {
        this.contacts.add(contact);
    }

    public MedicalHistory getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(MedicalHistory medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public List<CausedEvent> getCausedEvents() {
        return causedEvents;
    }

    public void addActionOnCondition(CausedEvent onCondition) {
        this.causedEvents.add(onCondition);
    }

    public String getPassword() {
        return password;
    }

    /**
     * Sets the password
     *
     * @param password - password
     */
    public void setPassword(String password) {
//        MessageDigest md;
//        try {
//            md = MessageDigest.getInstance("SHA-256");
//            md.update(password.getBytes("UTF-8"));
//            byte[] digest = md.digest();
//            String encodedPassword = Base64.encodeBase64String(digest);
//            this.password = encodedPassword;
//        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
//            logger.log(Level.SEVERE, "Password creation failed", e);
//            throw new RuntimeException(e);
//        }
        this.password = password;
    }

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }

    public BirthCertificate getBirthCertificate() {
        return birthCertificate;
    }

    public void setBirthCertificate(BirthCertificate birthCertificate) {
        this.birthCertificate = birthCertificate;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void addEmail(Long id, Email email) {
        this.emails.put(id, email);
    }

    public Collection<Email> getEmails() {
        return this.emails.values();
    }

    public Email getEmail(Long id) {
        return this.emails.get(id);
    }

    public void addActivationLink(ActivationLink link) {
        this.activationLinks.add(link);
    }
    
    public List<ActivationLink> getActivationLinks() {
        return this.activationLinks;
    }

    public boolean isEmailActivated() {
        return emailActivated;
    }

    public void setEmailActivated(boolean emailActivated) {
        this.emailActivated = emailActivated;
    }
    
    

}
