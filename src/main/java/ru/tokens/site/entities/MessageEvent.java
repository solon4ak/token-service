package ru.tokens.site.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 *
 * @author solon4ak
 */
public class MessageEvent implements Serializable {

    private long id;
    private User user;

    private DataEntry dataEntry;

    private List<Contact> contacts;

    private final LocalDate createDate;
    private LocalDate startDate;
    private LocalDate nextCheckDate;
    private LocalDate firedDate;

    // checking email sending interval
    private String checkingInterval;

    // состояние события
    private MessageEventStatus status;
    private final boolean started;
    private boolean executed;

    // флаг пролонгации
    private String prolongationToken;
    private boolean prolonged;
    private boolean waitingProlongation;

    public MessageEvent() {
        this.createDate = LocalDate.now();
        this.status = MessageEventStatus.PENDING;
        this.started = false;
        this.executed = false;
        this.prolonged = false;
        this.waitingProlongation = false;
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

    public LocalDate getCreateDate() {
        return createDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getNextCheckDate() {
        return nextCheckDate;
    }

    public void setNextCheckDate(LocalDate nextCheckDate) {
        this.nextCheckDate = nextCheckDate;
    }

    public LocalDate getFiredDate() {
        return firedDate;
    }

    public void setFiredDate(LocalDate firedDate) {
        this.firedDate = firedDate;
    }

    public String getCheckingInterval() {
        return checkingInterval;
    }

    public void setCheckingInterval(String checkingInterval) {
        this.checkingInterval = checkingInterval;
    }

    public MessageEventStatus getStatus() {
        return status;
    }

    public void setStatus(MessageEventStatus status) {
        this.status = status;
    }

    public String getProlongationToken() {
        return prolongationToken;
    }

    public void setProlongationToken(String prolongationToken) {
        this.prolongationToken = prolongationToken;
    }

    public boolean isProlonged() {
        return prolonged;
    }

    public void setProlonged(boolean prolonged) {
        this.prolonged = prolonged;
    }

    public boolean isStarted() {
        return this.status == MessageEventStatus.STARTED;
    }

    public boolean isWaitingProlongation() {
        return waitingProlongation;
    }

    public void setWaitingProlongation(boolean waitingProlongation) {
        this.waitingProlongation = waitingProlongation;
    }

    public boolean isExecuted() {
        return this.status == MessageEventStatus.FIRED;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }


    public enum MessageEventStatus {
        PENDING, STARTED, CANCELED, FIRED
    }
}
