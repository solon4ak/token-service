package ru.tokens.site.utils;

import java.io.File;
import java.time.Instant;
import java.util.Date;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import ru.tokens.site.entities.Attachment;
import ru.tokens.site.entities.Email;
import ru.tokens.site.entities.Token;

/**
 *
 * @author solon4ak
 */
public class EmailSender {
    
    private static final Logger log = LogManager.getLogger(EmailSender.class);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    @Qualifier("fileService")
    private FileUtil fileUtil;

    public void sendEmail(Token token, Email email) {
        
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(email.getTo());
            helper.setFrom(new InternetAddress(email.getFrom()));
            helper.setSubject(email.getSubject());
            helper.setText(email.getContent());
            helper.setSentDate(new Date());

            for (Attachment attachment : email.getAttachments()) {
                String path = fileUtil.getStorageDirectory(token);
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

    public JavaMailSender getMailSender() {
        return mailSender;
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
}
