package com.rushiwarade.emailsender.service.impl;

import com.rushiwarade.emailsender.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    private Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);


    @Override
    public void sendEmail(String to, String subject, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        simpleMailMessage.setFrom("rushikeshwarade12@gmail.com");
        javaMailSender.send(simpleMailMessage);
        logger.info("Email has been send");
    }

    @Override
    public void sendEmail(String[] to, String subject, String message) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        simpleMailMessage.setFrom("rushikeshwarade12@gmail.com");
        javaMailSender.send(simpleMailMessage);
        logger.info("Email has been send");

    }

    @Override
    public void sendMessageWithHtml(String to, String subject, String htmlContent) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom("rushikeshwarade12@gmail.com");
            helper.setText(htmlContent, true);
            javaMailSender.send(mimeMessage);
            logger.info("Email has been send with html content");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendEmailWithFile(String to, String subject, String message, InputStream inputStream, String fileName) {


        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = null;
        File file = null;
        try {
            helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(message,true);
            helper.setFrom("rushikeshwarade12@gmail.com");
            file = new File("src/main/resources/email/" + fileName);
            Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            FileSystemResource fileSystemResource = new FileSystemResource(file);

            helper.addAttachment(Objects.requireNonNull(fileSystemResource.getFilename()), file);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        } finally {
// Delete the file after sending the email
            if (file.exists() && !file.delete()) {
                System.err.println("Failed to delete temporary file: " + file.getAbsolutePath());
            }
        }


    }




}
