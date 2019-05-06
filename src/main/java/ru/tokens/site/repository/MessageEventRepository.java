package ru.tokens.site.repository;

import java.util.Collection;
import ru.tokens.site.entities.MessageEvent;
import ru.tokens.site.entities.User;

/**
 *
 * @author solon4ak
 */
public interface MessageEventRepository {
 
    MessageEvent findById(long id);
    
    void save(MessageEvent event);
    
    void update(MessageEvent event);
    
    void delete(long id);
    
    Collection<MessageEvent> list(User user);
    
    Collection<MessageEvent> list();
}
