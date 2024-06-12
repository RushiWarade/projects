package com.rushiwarade.emailsender;

import com.rushiwarade.emailsender.service.MailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmailSenderTest {

    @Autowired
    private MailService mailService ;

    @Test
    void emailSendTest(){
        System.out.println("Sending email");


        mailService.sendEmail("rushiwarade023@gmail.com","This is test mail","Hello");


    }

    @Test
    void emailSendWithHtml(){
        String htmlContent = "<h1 >Hello Rushi</h1> ";
        mailService.sendMessageWithHtml("rushiwarade023@gmail.com", "this is test mail", "hello");
    }

}
