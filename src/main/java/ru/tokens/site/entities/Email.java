package ru.tokens.site.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author solon4ak
 */
public class Email implements Serializable {
    
    private String from;
    private String to;
    private String subject;
    private String content;
    private Instant created;
    private Instant sent;
    
    private List<Attachment> attachments = new LinkedList<>();

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getSent() {
        return sent;
    }

    public void setSent(Instant sent) {
        this.sent = sent;
    }
    
    

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }    
    
}
