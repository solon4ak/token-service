package ru.tokens.site.entities;

import java.math.BigDecimal;
import java.time.Instant;

/**
 *
 * @author solon4ak
 */
public class UserBalance {
    
    private BigDecimal ammount;
    private Instant payedTill;

    public UserBalance() {
    }

    public UserBalance(BigDecimal ammount, Instant payedTill) {
        this.ammount = ammount;
        this.payedTill = payedTill;
    }
    
    public BigDecimal getAmmount() {
        return ammount;
    }

    public void setAmmount(BigDecimal ammount) {
        this.ammount = ammount;
    }

    public Instant getPayedTill() {
        return payedTill;
    }

    public void setPayedTill(Instant payedTill) {
        this.payedTill = payedTill;
    }
    
    public Long getPayedTillMillies() {
        return this.getPayedTill().toEpochMilli();
    }
    
    
}
