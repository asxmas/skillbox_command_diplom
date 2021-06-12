package ru.skillbox.team13.service;

import javax.mail.MessagingException;

public interface MailService {

    void sendMessage(String to, String subject, String text) throws MessagingException;
}
