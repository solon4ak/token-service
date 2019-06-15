package ru.tokens.site.entities;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import ru.tokens.site.entities.shop.UserOrder;

/**
 *
 * @author solon4ak
 */
public class User implements Serializable {

    /**
     * User logger
     */
    private static final Logger LOGGER = Logger.getLogger("User");

    private long userId;

    /* User registration data */
    private String firstName;
    private String lastName;
    private String middleName;
    private String password;
    private String userEmailAddress;
    private String phoneNumber;
    private Address postAddress;
    private Instant registered;
    private boolean emailActivated;

    /* Token related data */
    private Token token;
    private Address tokenAddress;
    private Passport passport;
    private BirthCertificate birthCertificate;

    private LocalDate birthDate;
    private Image image;

    private final Map<Long, Contact> contacts = new LinkedHashMap<>();

    private final Map<Long, Email> emails = new Hashtable<>();

    private MedicalHistory medicalHistory;

    // Список действий, наступление которых обусловлено каким-то событием 
    // со своим индикатором наступления
    private Map<Long, MessageEvent> messageEvents = new Hashtable<>();
    
    private Map<Long, UserOrder> orders = new Hashtable<>();

    public User() {
        super();
        this.emailActivated = false;
    }

    public User(String firstName, String lastName, String email,
            String password, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userEmailAddress = email;
        this.birthDate = birthDate;
        if (password != null) {
            this.setPassword(password);
        }
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getUserEmailAddress() {
        return userEmailAddress;
    }

    public void setUserEmailAddress(String userEmailAddress) {
        this.userEmailAddress = userEmailAddress;
    }

    public Address getTokenAddress() {
        return tokenAddress;
    }

    public void setTokenAddress(Address tokenAddress) {
        this.tokenAddress = tokenAddress;
    }

    public Address getPostAddress() {
        return postAddress;
    }

    public void setPostAddress(Address postAddress) {
        this.postAddress = postAddress;
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

    public Collection<Contact> getContacts() {
        return this.contacts.values();
    }

    public Contact getContact(Long id) {
        return this.contacts.get(id);
    }

    public void addContact(Contact contact) {
        this.contacts.put(contact.getContactId(), contact);
    }

    public void deleteContact(Long id) {
        this.contacts.remove(id);
    }

    public int getNumberOfContacts() {
        return this.contacts.size();
    }

    public MedicalHistory getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(MedicalHistory medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public Map<Long, MessageEvent> getMessageEvents() {
        return messageEvents;
    }

    public Collection<MessageEvent> getMessageEventsList() {
        return this.getMessageEvents().values();
    }

    public void addMessageEvent(MessageEvent msgEvent) {
        this.messageEvents.put(msgEvent.getId(), msgEvent);
    }

    public void deleteMessageEvent(Long id) {
        this.messageEvents.remove(id);
    }

    public MessageEvent getMessageEvent(Long id) {
        return this.messageEvents.get(id);
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

    public boolean isEmailActivated() {
        return emailActivated;
    }

    public void setEmailActivated(boolean emailActivated) {
        this.emailActivated = emailActivated;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public Instant getRegistered() {
        return registered;
    }

    public void setRegistered(Instant registered) {
        this.registered = registered;
    }

    public Map<Long, UserOrder> getOrders() {
        return orders;
    }
    
    public List<UserOrder> getUserOrders() {
        return new LinkedList<>(this.getOrders().values());
    }
    
    public UserOrder addOrder(UserOrder order) {
        this.getOrders().put(order.getOrderId(), order);
        return order;
    }
    
    public UserOrder removeOrder(UserOrder order) {
        this.getOrders().remove(order.getOrderId());
        return order;
    }
    
    public int getNumberOfOrders() {
        return this.getOrders().size();
    }

    @Override
    public String toString() {
        return lastName + ", " + firstName;
    }

}
