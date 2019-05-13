package ru.tokens.site.services;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimerTask;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import ru.tokens.site.entities.MessageEvent;
import ru.tokens.site.utils.MessageEventHelper;

/**
 *
 * @author solon4ak
 */
@Service(value = "schedular")
public class MessageEventTimerServiceImpl implements MessageEventTimerService {
    
    @Autowired
    private Environment environment;
    
    @Autowired
    private ThreadPoolTaskScheduler scheduler;
    
    @Autowired
    private EmailSender emailSender;
    
    private static final Logger LOG = LogManager.getLogger(MessageEventTimerServiceImpl.class.getName());
    
    @Async
    @Override
    public synchronized void fireMessageEventCheckingTimer(MessageEvent event, long delay) {
        LOG.warn("insight fireMessageEventCheckingTimer method");
        TimerTask checkingTask = new TimerTask() {
            @Override
            public void run() {
                if (event.getStatus() == MessageEvent.MessageEventStatus.STARTED) {
                    LOG.warn("fireMessageEventCheckingTimer task start running");
                    // если проверочный токен совпадает
                    if (event.isProlonged()) {
                        LOG.warn("prolonged");
                        final Long delay = new Long(
                                MessageEventHelper
                                        .getIntervals()
                                        .get(event.getCheckingInterval()
                                        )
                        );
                        
                        fireMessageEventPeriodicalTimer(event,
                                delay - Long.valueOf(environment.getProperty("timer.confirm.interval"))
                        );
                        LOG.info("starting fireMessageEventPeriodicalTimer method");
                    } else {
                        LOG.info("message event wasn't prolonged");
                        emailSender.prepareSendEventHappenedEmail(event);

                        event.setStatus(MessageEvent.MessageEventStatus.FIRED);
                        event.setFiredDate(LocalDate.now());
                    }
                }
                System.out.println("-----> fireMessageEventCheckingTimer() executed");
            }
        };

//        Date sDate = this.calculateExecutionDate(delay);
        Date sDate = this.calculateExecutionDateMock(delay);
        scheduler.schedule(checkingTask, sDate);
//        scheduler.getScheduledExecutor().schedule(checkingTask, delay, TimeUnit.MINUTES);
    }
    
    @Async
    @Override
    public synchronized void fireMessageEventPeriodicalTimer(MessageEvent event, long delay) {
        LOG.warn("insight fireMessageEventPeriodicalTimer method");
        TimerTask periodicalTask = new TimerTask() {
            @Override
            public void run() {
                if (event.getStatus() == MessageEvent.MessageEventStatus.STARTED) {
                    LOG.warn("fireMessageEventPeriodicalTimer task start running");
                    // отправить проверочное письмо
                    final String eventProlongationToken = UUID.randomUUID().toString();
                    event.setProlongationToken(eventProlongationToken);
                    LOG.warn("Created eventProlongationToken: {}", event.getProlongationToken());
                    emailSender.sendTriggerEmail(event);
                    System.out.println("Sending prolongation email");
                    LOG.warn("starting fireMessageEventCheckingTimer");
                    
                    event.setProlonged(false);
                    fireMessageEventCheckingTimer(
                            event,
                            Long.valueOf(environment.getProperty("timer.confirm.interval")
                            )
                    );
                    event.setWaitingProlongation(true);
                }
                System.out.println("-----> fireMessageEventPeriodicalTimer() executed");
            }
        };

//        Date sDate = this.calculateExecutionDate(delay);
        Date sDate = this.calculateExecutionDateMock(delay);
        event.setNextCheckDate(sDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate());
        scheduler.schedule(periodicalTask, sDate);
//        scheduler.getScheduledExecutor().schedule(checkingTask, delay, TimeUnit.MINUTES);
    }
    
    public synchronized Date calculateExecutionDate(long delay) {
        LocalDate ld = LocalDate.now();
        LocalDate scheduled = ld.plusDays(delay);
        return Date.from(scheduled.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
    
    public synchronized Date calculateExecutionDateMock(long delay) {
        Date date = new Date();
        long millies = date.getTime();
        return new Date(millies + delay * 30 * 1000);
    }
}
