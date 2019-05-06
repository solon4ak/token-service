package ru.tokens.site.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import ru.tokens.site.entities.MessageEvent;
import ru.tokens.site.entities.User;

/**
 *
 * @author solon4ak
 */
@Repository
public class MessageEventRepositoryInMemoryImpl implements MessageEventRepository {

    private Map<Long, MessageEvent> repository = new Hashtable<>();
    
    private volatile long MESSAGE_EVENT_ID_SEQUENCE = 1L;

    private synchronized long getNextMessageEventId() {
        return this.MESSAGE_EVENT_ID_SEQUENCE++;
    }

    @Override
    public MessageEvent findById(long id) {
        return this.repository.get(id);
    }

    @Override
    public void save(MessageEvent event) {
        event.setId(this.getNextMessageEventId());
        this.repository.put(event.getId(), event);
    }

    @Override
    public void update(MessageEvent event) {
        this.repository.put(event.getId(), event);
    }

    @Override
    public void delete(long id) {
        this.repository.remove(id);
    }

    @Override
    public Collection<MessageEvent> list(User user) {
        List<MessageEvent> userMessageEvents = new ArrayList<>();

        for (MessageEvent me : this.repository.values()) {
            if (me.getUser() == user) {
                userMessageEvents.add(me);
            }
        }

        return userMessageEvents;
    }

    @Override
    public Collection<MessageEvent> list() {
        return this.repository.values();
    }

}
