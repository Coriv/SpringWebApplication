package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SimpleMailService {

    private final JavaMailSender javaMailSender;
    private final MailCreatorService mailCreatorService;

    public void send(final Mail mail) {
        log.info("Starting email preparation...");
        try {
            javaMailSender.send(createMimeMailMessage(mail));
            log.info("Email has been sent.");
        } catch (MailException e) {
            log.error("Failed to process email sending: " + e.getMessage());
        }
    }

    private MimeMessagePreparator createMimeMailMessage(final Mail mail) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            if(mail.getShortUrl() != null) {
                messageHelper.setText(mailCreatorService.buildTrelloCardEmail(mail));
            } else {
                messageHelper.setText(mailCreatorService.buildScheduledDatabaseInfoMail(mail));
            }
        };
    }

    private SimpleMailMessage createMailMessage(final Mail mail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mailCreatorService.buildTrelloCardEmail(mail));
        Optional.ofNullable(mail.getToCc()).ifPresent(
                toCc -> mailMessage.setCc(toCc)
        );
        return mailMessage;
    }
}
