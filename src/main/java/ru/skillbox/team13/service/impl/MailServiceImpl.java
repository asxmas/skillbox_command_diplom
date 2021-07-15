package ru.skillbox.team13.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.skillbox.team13.service.MailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender emailSender;

    public void sendMessage(String to, String subject, String text) throws MessagingException {
        //сообщение в формате html
        MimeMessage htmlMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(htmlMessage);
        helper.setFrom("noreply@stream-team13.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text,true);
        log.info("Sending message to:'{}', subj:'{}', text:'{}'", to, subject, text);
        emailSender.send(htmlMessage);
    }
}