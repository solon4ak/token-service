package ru.tokens.site.utils;

import java.io.File;
import java.time.Instant;
import java.util.Date;
import java.util.logging.Level;
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
import org.springframework.stereotype.Service;
import ru.tokens.site.entities.Attachment;
import ru.tokens.site.entities.Email;
import ru.tokens.site.entities.User;
import ru.tokens.site.registration.OnRegistrationCompleteEvent;

/**
 *
 * @author solon4ak
 */
@Service
public class EmailSender {

    private static final Logger log = LogManager.getLogger(EmailSender.class);

    @Autowired
    private Environment environment;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    @Qualifier("fileService")
    private FileUtil fileUtil;

    public void sendEmail(Email email) {

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

    public void sendVerificationEmail(final OnRegistrationCompleteEvent event,
            final User user, final String activationUUIDString) {

        final String recipientAddress = user.getUserEmailAddress();
        final String subject = "Registration Confirmation";
        final String confirmationUrl = event.getAppUrl()
                + "/user/registrationConfirm?token=" + activationUUIDString;
        // final String message = messages.getMessage("message.regSucc", null, event.getLocale());
        final String message = "Confirm your registration";
        String htmlMessage = message + " \r\n" + "<a href='" + confirmationUrl + "'>Confirm</a>";

        try {
            final MimeMessage mimeMessage = mailSender.createMimeMessage();
            final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
            
//            final SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
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

    public JavaMailSender getMailSender() {
        return mailSender;
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
}
