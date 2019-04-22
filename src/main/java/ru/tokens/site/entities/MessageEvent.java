package ru.tokens.site.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author solon4ak
 */
public class MessageEvent implements Serializable {
    
    private long id;
//    private User user;
    
    private DataEntry dataEntry;
    
    private List<Contact> contacts;
   
    private final LocalDate startDate;
    private LocalDate endDate;
    
    // checking email sending interval
    private String checkingInterval;
    private boolean executed;
    

    public MessageEvent() {
        startDate = LocalDate.now();       
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }

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

    public LocalDate getStartDate() {
        return startDate;
    }
    
    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getCheckingInterval() {
        return checkingInterval;
    }

    public void setCheckingInterval(String checkingInterval) {
        this.checkingInterval = checkingInterval;
    }

    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }

    
    
}
