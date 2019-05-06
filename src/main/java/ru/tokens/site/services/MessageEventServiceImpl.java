package ru.tokens.site.services;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tokens.site.entities.MessageEvent;
import ru.tokens.site.entities.User;
import ru.tokens.site.repository.MessageEventRepository;

/**
 *
 * @author solon4ak
 */
@Service
public class MessageEventServiceImpl implements MessageEventService {
    
    @Autowired
    private MessageEventRepository eventRepository;

    @Override
    public void addMessageEvent(MessageEvent messageEvent) {
        if (messageEvent.getId() < 1) {
            this.eventRepository.save(messageEvent);
        } else {
            this.eventRepository.update(messageEvent);
        }        
    }

    @Override
    public MessageEvent findMessageEventById(long id) {
        return this.eventRepository.findById(id);
    }

    @Override
    public void deleteMessageEvent(long id) {
        this.eventRepository.delete(id);
    }

    @Override
    public Collection<MessageEvent> getAllMessageEvents() {
        return this.eventRepository.list();
    }

    @Override
    public Collection<MessageEvent> getMessageEventsForUser(User user) {
        return this.eventRepository.list(user);
    }
    
}
