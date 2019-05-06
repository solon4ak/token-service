package ru.tokens.site.entities;

import java.io.Serializable;
import java.time.Instant;

/**
 *
 * @author solon4ak
 */
public class Token implements Serializable {
        
    private long tokenId;
    private String uuidString;
    private String activationCode;
    private Instant activatedDate;
    private boolean activated;
    private User user;

    public Token() {
        this.activated = false;
    }

    public String getUuidString() {
        return uuidString;
    }

    public void setUuidString(String uuidString) {
        this.uuidString = uuidString;
    }

    public long getTokenId() {
        return tokenId;
    }

    public void setTokenId(long tokenId) {
        this.tokenId = tokenId;
    }    

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public Instant getActivatedDate() {
        return activatedDate;
    }

    public void setActivatedDate(Instant activatedDate) {
        this.activatedDate = activatedDate;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }   
    
    
}
