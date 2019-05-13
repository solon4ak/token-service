package ru.tokens.site.services;

import java.io.File;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import ru.tokens.site.entities.Attachment;
import ru.tokens.site.entities.Contact;
import ru.tokens.site.entities.Email;
import ru.tokens.site.entities.MessageEvent;
import ru.tokens.site.entities.User;
import ru.tokens.site.registration.OnRegistrationCompleteEvent;

/**
 *
 * @author solon4ak
 */
@Component
public class EmailSender {

    private static final Logger log = LogManager.getLogger(EmailSender.class);

    @Autowired
    private Environment environment;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    @Qualifier("fileService")
    private FileUtil fileUtil;

    public synchronized void prepareSendEventHappenedEmail(final MessageEvent event) {
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
            this.sendEventHappenedEmail(e);
        }
    }

    public synchronized void sendEventHappenedEmail(Email email) {

        log.warn("Insight sendEventHappenedEmail method");
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(new InternetAddress(email.getTo()));
//            helper.setFrom(new InternetAddress("tag4life@yandex.ru"));
            helper.setFrom(environment.getProperty("mail.smtp.user"));
            helper.setSubject(email.getSubject());
            helper.setText(email.getContent());
            helper.setSentDate(new Date());

            for (Attachment attachment : email.getAttachments()) {
                String path = fileUtil.getStorageDirectory();
                File file = new File(path
                        + File.separator
                        + attachment.getNewFileName());
                helper.addAttachment(attachment.getName(), file);
            }

            mailSender.send(message);
            email.setSent(Instant.now());
            System.out.println("Email sending complete.");
        } catch (MessagingException | MailException e) {
            log.error("Email sending error {}", e.getMessage());
        }
    }

    public synchronized void sendVerificationEmail(final OnRegistrationCompleteEvent event,
            final User user, final String activationUUIDString) {

        final String recipientAddress = user.getUserEmailAddress();
        final String subject = "tag4life: registration e-mail confirmation";
        final String confirmationUrl = event.getAppUrl()
                + "/user/registrationConfirm?token=" + activationUUIDString;
        // final String message = messages.getMessage("message.regSucc", null, event.getLocale());
        final String message = "Confirm your registration<br />";
        final String htmlMessage = message + " \r\n" + "<a href='" + confirmationUrl + "'>Confirm</a>";

        try {
            final MimeMessage mimeMessage = mailSender.createMimeMessage();
            final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

            helper.setTo(new InternetAddress(recipientAddress));
            helper.setFrom(environment.getProperty("mail.smtp.user"));
            helper.setSubject(subject);
            mimeMessage.setContent(htmlMessage, "text/html");
            mailSender.send(mimeMessage);

            System.out.println("User email address verification email sending complete.");
        } catch (MessagingException ex) {
            log.error("Email sending error {}", ex.getMessage());
        }
    }

    public synchronized void sendTriggerEmail(final MessageEvent event) {

        log.warn("Insight sendTriggerEmail method");

        final String recipientAddress = event.getUser().getUserEmailAddress();
        final String prolongationUUIDString = event.getProlongationToken();
        final String subject = "tag4life: periodical user status checking email";
        final String contextUrl = environment.getProperty("host.name");
        final String confirmationUrl = contextUrl + "/tkn/token/prolongationConfirm/"
                + event.getId() + "/" + prolongationUUIDString;
        final String message = "Вы получили это письмо так как используете сервис <br />"
                + "отсроченного отправления сообщения. Перейдите по ссылке в теле <br />"
                + "письма для продления действия сервиса. Если вы не перейдете по <br />"
                + "ссылке или не подтвердите свое намерение продолжить действие <br />"
                + "запущенного вами сервиса на вашей странице, в течение <b>5 дней</b>, <br />"
                + "сервис запустит отправку созданных вами сообщений.<br />";
        final String htmlMessage = message + "<a href='" + confirmationUrl + "'>Confirm</a>";

        try {
            final MimeMessage mimeMessage = mailSender.createMimeMessage();
            final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

            helper.setTo(new InternetAddress(recipientAddress));
            helper.setFrom(environment.getProperty("mail.smtp.user"));
            helper.setSubject(subject);
            mimeMessage.setContent(htmlMessage, "text/html;charset=UTF-8");
            mailSender.send(mimeMessage);

            log.warn("Message Event prolongation email sending complete");
        } catch (MessagingException ex) {
            log.error("Email sending error {}", ex.getMessage());
        }
    }

    public synchronized void sendNewPasswordEmail(final String email, final String newPassword) {

        final String subject = "tag4life: password reset";
        final String body = "Your new password: " + newPassword;

        final SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setFrom(environment.getProperty("mail.smtp.user"));
        mailMessage.setSubject(subject);
        mailMessage.setText(body);
        mailSender.send(mailMessage);

        System.out.println("User reset password email sending complete.");
    }

    public synchronized void sendSimpleEmail(final String email, final String subject,
            final String body) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setFrom(environment.getProperty("mail.smtp.user"));
        mailMessage.setSubject(subject);
        mailMessage.setText(body);
        mailSender.send(mailMessage);

        System.out.println("Simple email was send. Subject: " + subject);
    }

    public JavaMailSender getMailSender() {
        return mailSender;
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
}
