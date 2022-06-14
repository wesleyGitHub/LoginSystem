package com.example.demo.email;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Service
@AllArgsConstructor
public class EmailService implements EmailSender {
    private final static Logger LOGGER =
            LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender javaMailSender;

    @Override
    @Async
    public void send(String address, String context) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            mimeMessageHelper.setText(context, true);
            mimeMessageHelper.setTo(address);
            mimeMessageHelper.setSubject("Confirm your email.");
            mimeMessageHelper.setFrom("hello@amigoscode.com");
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("failed to send email", e);
            throw new IllegalStateException("failed to send email");
        }
    }
}
