package ru.tokens.site.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (int) (this.tokenId ^ (this.tokenId >>> 32));
        hash = 17 * hash + Objects.hashCode(this.uuidString);
        hash = 17 * hash + Objects.hashCode(this.activationCode);
        hash = 17 * hash + Objects.hashCode(this.activatedDate);
        hash = 17 * hash + (this.activated ? 1 : 0);
        hash = 17 * hash + Objects.hashCode(this.user);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Token other = (Token) obj;
        if (this.tokenId != other.tokenId) {
            return false;
        }
        if (this.activated != other.activated) {
            return false;
        }
        if (!Objects.equals(this.uuidString, other.uuidString)) {
            return false;
        }
        if (!Objects.equals(this.activationCode, other.activationCode)) {
            return false;
        }
        if (!Objects.equals(this.activatedDate, other.activatedDate)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Token{" + "tokenId=" + tokenId + ", uuidString=" + uuidString + ", activated=" + activated + ", user=" + user + '}';
    }
    
    
}
