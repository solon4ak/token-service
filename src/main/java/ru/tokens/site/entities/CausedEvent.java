package ru.tokens.site.entities;

import java.util.Date;
import java.util.List;

/**
 *
 * @author solon4ak
 */
public class CausedEvent {
    
    private long id;
    private User user;
    
    private DataEntry dataEntry;
    
    private List<Contact> contacts;
   
    private final Date startDate;
    private Date endDate;
    
    // checking email sending interval
    private int emailsInterval;
    private boolean executed;

    public CausedEvent() {
        startDate = new Date();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DataEntry getDataEntry() {
        return dataEntry;
    }

    public void setDataEntry(DataEntry dataEntry) {
        this.dataEntry = dataEntry;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public Date getStartDate() {
        return startDate;
    }
    
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getEmailsInterval() {
        return emailsInterval;
    }

    public void setEmailsInterval(int emailsInterval) {
        this.emailsInterval = emailsInterval;
    }

    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }
        
    
}
