package ru.tokens.site.repository;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import ru.tokens.site.entities.Attachment;

/**
 *
 * @author solon4ak
 */
@Repository
public class AttachmentRepositoryImpl implements AttachmentRepository {
    
    private Map<Long, Attachment> repo = new Hashtable<>();
    
    private volatile long ATTACHMENT_ID_SEQUENCE = 1L;
    
    private synchronized long getNextId() {
        return this.ATTACHMENT_ID_SEQUENCE ++;
    }

    @Override
    public List<Attachment> list() {
        return new ArrayList<>(this.repo.values());
    }

    @Override
    public Attachment get(long id) {
        return this.repo.get(id);
    }

    @Override
    public void create(Attachment attachment) {
        attachment.setId(this.getNextId());
        this.repo.put(attachment.getId(), attachment);
    }

    @Override
    public void update(Attachment attachment) {
        this.repo.put(attachment.getId(), attachment);
    }

    @Override
    public void delete(long id) {
        this.repo.remove(id);
    }
    
}
