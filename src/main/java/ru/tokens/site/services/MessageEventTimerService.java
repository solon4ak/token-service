package ru.tokens.site.services;

import org.springframework.scheduling.annotation.Async;
import ru.tokens.site.entities.MessageEvent;

/**
 *
 * @author solon4ak
 */
public interface MessageEventTimerService {
    
    @Async
    void fireMessageEventPeriodicalTimer(MessageEvent event, long delay);
    
    @Async
    void fireMessageEventCheckingTimer(MessageEvent event, long delay);
        
}
