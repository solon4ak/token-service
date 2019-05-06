package ru.tokens.site.services;

import ru.tokens.site.entities.MessageEvent;

/**
 *
 * @author solon4ak
 */
public interface TimerProlongationLinkService {
    
//    void runPeriodicalTimer(MessageEvent event);
//    
//    void runCheckingTimer(MessageEvent event);
    
    void startTimerService(MessageEvent event);
    
    void stopTimerService(MessageEvent event);
    
    void checkProlongationToken(MessageEvent event, String tokenString);
    
}
