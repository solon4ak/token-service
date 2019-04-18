package ru.tokens.site.entities;

import java.util.Date;
import java.util.List;

/**
 *
 * @author solon4ak
 */
public class ActionOnCondition {
    
    private long id;
    private User user;
    
    private List<Attachment> attachments;
    
    private List<Contact> contacts;
    
    private Date startDate;
    private Date endDate;
    
    private int emailsInterval;
    private boolean executed;
    
}
