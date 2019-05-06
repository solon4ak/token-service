package ru.tokens.site.services;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.TimerTask;
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
import ru.tokens.site.utils.EmailSender;
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
                    emailSender.sendTriggerEmail(event);
                    LOG.warn("starting fireMessageEventCheckingTimer");
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

//    @Override
//    public synchronized Timer createRepeatedAction(final MessageEvent event, 
//            final String contextUrl, final User user) {
//
//        final TimerTask timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                sender.sendTriggerEmail(event, contextUrl, user);
//                System.out.println("Event subject: " + event.getDataEntry().getSubject());
//            }
//        };
//
//        final Long period = new Long(
//                MessageEventHelper
//                        .getIntervals()
//                        .get(event.getCheckingInterval()
//                        )
//        );
//
////        final Long periodInMinutes = period * DAY;
//        final Long periodInMinutes = period * MINUTE;
//        final Timer timer = new Timer(event.getDataEntry().getSubject() + "-periodical Timer");
//        timer.scheduleAtFixedRate(timerTask, periodInMinutes, periodInMinutes);
//        event.setPeriodicalTimer(timer);
//
//        return timer;
//    }
//
//    @Override
//    public synchronized Timer createCheckingTimer(final MessageEvent event) {
//
//        final List<Contact> contacts = event.getContacts();
//        final String subject = event.getDataEntry().getSubject();
//        final String body = event.getDataEntry().getBody();
//        final Collection<Attachment> attachments = event.getDataEntry().getAttachments();
////        String from = event.getUser().getEmail();
//
//        final TimerTask timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                for (Contact contact : contacts) {
//                    final Email e = new Email();
//                    e.setTo(contact.getEmail());
////                    e.setFrom(from);
//                    e.setSubject(subject);
//                    e.setContent(body);
//                    e.setAttachments(new LinkedList<>(attachments));
//                    e.setCreated(Instant.now());
//                    sender.sendEventHappenedEmail(e);
//                }
//
//                System.out.println("Event subject: " + event.getDataEntry().getSubject());
//            }
//        };
//
////        final Long delay = Long.valueOf(environment.getProperty("timer.confirm.interval")) * DAY;
//        final Long delay = Long.valueOf(environment.getProperty("timer.confirm.interval")) * MINUTE;
//        final Timer timer = new Timer(event.getDataEntry().getSubject() + "-checking Timer");
//        timer.schedule(timerTask, delay);
//        event.setConfirmationTimer(timer);
//        return timer;
//    }
//
//    @Override
//    public synchronized void cancelTimer(final Timer timer) {
//        timer.cancel();
//        timer.purge();
//    }
}
