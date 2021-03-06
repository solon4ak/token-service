package ru.tokens.site.services;

import java.time.LocalDate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tokens.site.entities.MessageEvent;
import ru.tokens.site.utils.MessageEventHelper;

/**
 *
 * @author solon4ak
 */
@Service
public class TimerProlongationLinkServiceImpl implements TimerProlongationLinkService {

    @Autowired
    private MessageEventTimerService timerService;   
    
    private static final Logger LOG = LogManager.getLogger("TimerProlongationLinkService");

    @Override
    public void startTimerService(MessageEvent event) {
        if (event.getStatus() == MessageEvent.MessageEventStatus.PENDING) {

            final Long delay = new Long(
                    MessageEventHelper
                            .getIntervals()
                            .get(event.getCheckingInterval()
                            )
            );
            
            event.setStatus(MessageEvent.MessageEventStatus.STARTED);
            event.setStartDate(LocalDate.now());
            timerService.fireMessageEventPeriodicalTimer(event, delay);            
            LOG.warn("fireMessageEventPeriodicalTimer was started. "
                    + "MessageEventStatus was setted to started.");
        }
    }

    @Override
    public void stopTimerService(MessageEvent event) {
        if (event.getStatus() == MessageEvent.MessageEventStatus.STARTED) {
            event.setStatus(MessageEvent.MessageEventStatus.PENDING);
            LOG.warn("MessageEventStatus was setted to pending");
        }
    }

    @Override
    public void checkProlongationToken(MessageEvent event, String tokenString) {   
        if (event.getStatus().equals(MessageEvent.MessageEventStatus.STARTED)
                && tokenString.equals(event.getProlongationToken())) {
            event.setProlonged(true);
            LOG.info("Prolongation token was confirmed.");
        }       
    }

}
