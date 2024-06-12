package com.rushiwarade.emailsender.controller;

import com.rushiwarade.emailsender.entity.CustomResponse;
import com.rushiwarade.emailsender.entity.EmailRequest;
import com.rushiwarade.emailsender.service.MailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@CrossOrigin
@RequestMapping("/api/email")
public class SendEmailController {

    @Autowired
    private MailService mailService;

    @PostMapping("/send")
    public ResponseEntity<CustomResponse> sendEmail(@RequestBody EmailRequest emailRequest) {
        mailService.sendMessageWithHtml(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getMessage());

        return ResponseEntity.ok(
                CustomResponse.builder()
                        .message("Email send Successfully !!")
                        .httpStatus(HttpStatus.OK)
                        .success(true)
                        .build()
        );
    }

    @PostMapping("/send-with-file")
    public ResponseEntity<CustomResponse> sendWithFile(@RequestPart EmailRequest emailRequest, @RequestPart MultipartFile file) {

        try {
            mailService.sendEmailWithFile(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getMessage(), file.getInputStream(), file.getOriginalFilename());
            return ResponseEntity.ok(
                    CustomResponse.builder()
                            .message("Email send Successfully !!")
                            .httpStatus(HttpStatus.OK)
                            .success(true)
                            .build()
            );
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(CustomResponse.builder()
                            .message("Failed to send email: " + e.getMessage())
                            .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                            .success(false)
                            .build());
        }


    }


}
