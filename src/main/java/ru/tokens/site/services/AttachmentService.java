package ru.tokens.site.services;

import java.util.List;
import ru.tokens.site.entities.Attachment;

/**
 *
 * @author solon4ak
 */
public interface AttachmentService {
    
    List<Attachment> getAll();
    
    Attachment findById(long id);
    
    void save(Attachment attachment);
    
    void delete(long id);
}
