package ru.tokens.site.entities;

import java.time.Instant;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author solon4ak
 */
public class DataEntry {

    private long id;
    private String subject;
    private String body;
    private Instant dateCreated;
    private Map<Long, Attachment> attachments = new LinkedHashMap<>();

    public DataEntry() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Attachment getAttachment(long id) {
        return this.attachments.get(id);
    }
    
    public Attachment getAttachment(String name) {
        for (Map.Entry<Long, Attachment> entry : attachments.entrySet()) {
            Attachment attachment = entry.getValue();
            if(name.equals(attachment.getName())) {
                return attachment;
            }            
        }
        return null;
    }

    public Collection<Attachment> getAttachments() {
        return this.attachments.values();
    }

    public void addAttachment(Attachment attachment) {
        this.attachments.put(attachment.getId(), attachment);
    }

    public int getNumberOfAttachments() {
        return this.attachments.size();
    }
    
    public void deleteAttachment(long id) {
        this.attachments.remove(id);
    }
    
    public Map<Long, Attachment> getAttachmentsMap() {
        return this.attachments;
    }
    
}
