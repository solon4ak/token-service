package ru.tokens.site.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author solon4ak
 */
public class ActivationLink implements Serializable {
    
    private static final int EXPIRATION = 60 * 24;
    
    private Long id;
    private String token;
    
    private User user;
    private Date expiryDate;

    public ActivationLink(final String token) {
        super();
        this.token = token;
        
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    public ActivationLink(final String token, final User user) {
        super();
        this.token = token;
        this.user = user;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
    
    private Date calculateExpiryDate(final int expiryTimeInMinutes) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }
    
}
