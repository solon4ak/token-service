package ru.tokens.site.utils;

import static java.lang.ProcessBuilder.Redirect.to;
import java.time.Instant;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.tokens.site.entities.Attachment;
import ru.tokens.site.entities.Contact;
import ru.tokens.site.entities.Email;
import ru.tokens.site.entities.MessageEvent;

/**
 *
 * @author solon4ak
 */
@Service(value = "schedular")
public class EmailCheckingServiceImpl implements EmailCheckingService {

    @Autowired
    private EmailSender sender;

    private static final Logger log = LogManager.getLogger();

    @Override
    public ScheduledExecutorService createTimerService() {
        return Executors.newSingleThreadScheduledExecutor();
    }

    @Async
    @Override
    public void runTimerService(ScheduledExecutorService executor, MessageEvent event) {

        List<Contact> contacts = event.getContacts();
        String subject = event.getDataEntry().getSubject();
        String body = event.getDataEntry().getBody();
        Collection<Attachment> attachments = event.getDataEntry().getAttachments();
//        String from = event.getUser().getEmail();
        
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                for (Contact contact : contacts) {
                    Email e = new Email();
                    e.setTo(contact.getEmail());
//                    e.setFrom(from);
                    e.setSubject(subject);
                    e.setContent(body);
                    e.setAttachments(new LinkedList<>(attachments));
                    e.setCreated(Instant.now());
                    sender.sendEmail(e);
                }
                
                System.out.println("Event subject: " + event.getDataEntry().getSubject());
            }
        };

        Long period = new Long(
                MessageEventHelper
                        .getIntervals()
                        .get(event.getCheckingInterval()
                        )
        );

        executor.scheduleAtFixedRate(timerTask, period, period, TimeUnit.MINUTES);
    }

    @Override
    public void stopTimerService(ScheduledExecutorService executor) {
        executor.shutdownNow();
    }

}
