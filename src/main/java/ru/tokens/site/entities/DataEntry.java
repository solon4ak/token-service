package ru.tokens.site.entities;

import java.time.Instant;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 67 * hash + Objects.hashCode(this.subject);
        hash = 67 * hash + Objects.hashCode(this.body);
        hash = 67 * hash + Objects.hashCode(this.dateCreated);
        hash = 67 * hash + Objects.hashCode(this.attachments);
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
        final DataEntry other = (DataEntry) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.subject, other.subject)) {
            return false;
        }
        if (!Objects.equals(this.body, other.body)) {
            return false;
        }
        if (!Objects.equals(this.dateCreated, other.dateCreated)) {
            return false;
        }
        if (!Objects.equals(this.attachments, other.attachments)) {
            return false;
        }
        return true;
    }
    
    
    
}
