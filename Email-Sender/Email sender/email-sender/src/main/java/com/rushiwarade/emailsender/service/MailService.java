package com.rushiwarade.emailsender.service;

import java.io.File;
import java.io.InputStream;

public interface MailService {
    //    send message to single person
    void sendEmail(String to, String subject, String message);

    //    send message to multiple person
    void sendEmail(String[] to, String subject, String message);

    //    send message using html content
    void sendMessageWithHtml(String to, String subject, String htmlContent);

    //send message with attachment
//    void sendEmailWithFile(String to, String subject, String message, File file);

    void sendEmailWithFile(String to, String subject, String message, InputStream inputStream ,String fileName);

}
