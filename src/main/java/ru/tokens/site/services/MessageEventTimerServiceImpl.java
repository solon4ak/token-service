package ru.tokens.site.services;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import ru.tokens.site.entities.Attachment;
import ru.tokens.site.entities.Contact;
import ru.tokens.site.entities.Email;
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
                        LOG.warn("started fireMessageEventPeriodicalTimer method");
                    } else {
                        LOG.warn("not prolonged");
                        final List<Contact> contacts = event.getContacts();
                        final String subject = event.getDataEntry().getSubject();
                        final String body = event.getDataEntry().getBody();
                        final Collection<Attachment> attachments = event.getDataEntry().getAttachments();

                        for (Contact contact : contacts) {
                            final Email e = new Email();
                            e.setTo(contact.getEmail());
                            e.setSubject(subject);
                            e.setContent(body);
                            e.setAttachments(new LinkedList<>(attachments));
                            e.setCreated(Instant.now());
                            emailSender.sendEventHappenedEmail(e);
                        }

                        event.setStatus(MessageEvent.MessageEventStatus.FIRED);
                        event.setEndDate(LocalDate.now());
                    }
                }
                System.out.println("-----> fireMessageEventCheckingTimer() executed");
            }
        };

        scheduler.getScheduledExecutor().schedule(checkingTask, delay, TimeUnit.MINUTES);
    }

    @Async
    @Override
    public synchronized void fireMessageEventPeriodicalTimer(MessageEvent event, long delay) {
        LOG.warn("insight fireMessageEventPeriodicalTimer method");
        TimerTask checkingTask = new TimerTask() {
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

        scheduler.getScheduledExecutor().schedule(checkingTask, delay, TimeUnit.MINUTES);
    }
}
