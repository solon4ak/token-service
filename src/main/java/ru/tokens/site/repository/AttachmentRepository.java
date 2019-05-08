package ru.tokens.site.repository;

import java.util.List;
import ru.tokens.site.entities.Attachment;

/**
 *
 * @author solon4ak
 */
public interface AttachmentRepository {
    
    List<Attachment> list();
    
    Attachment get(long id);
    
    void create(Attachment attachment);
    
    void update(Attachment attachment);
    
    void delete(long id);
}
