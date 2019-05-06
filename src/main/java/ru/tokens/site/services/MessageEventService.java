package ru.tokens.site.services;

import java.util.Collection;
import ru.tokens.site.entities.MessageEvent;
import ru.tokens.site.entities.User;

/**
 *
 * @author solon4ak
 */
public interface MessageEventService {
    
    void addMessageEvent(MessageEvent messageEvent);
    
    MessageEvent findMessageEventById(long id);
    
    void deleteMessageEvent(long id);
    
    Collection<MessageEvent> getAllMessageEvents();
    
    Collection<MessageEvent> getMessageEventsForUser(User user);
    
}
