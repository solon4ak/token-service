package ru.tokens.site.utils;

import java.util.concurrent.ScheduledExecutorService;
import org.springframework.scheduling.annotation.Async;
import ru.tokens.site.entities.MessageEvent;

/**
 *
 * @author solon4ak
 */
public interface EmailCheckingService {
    
    ScheduledExecutorService createTimerService();
    
    @Async
    void runTimerService(ScheduledExecutorService executor, MessageEvent event);
    
    public void stopTimerService(ScheduledExecutorService executor);
    
}
