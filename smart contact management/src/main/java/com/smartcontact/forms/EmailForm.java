package com.smartcontact.forms;

import org.springframework.beans.factory.annotation.Value;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailForm {

    // @Value("${spring.mail.username}")
    private String emailFrom;

    private String emailTo;

    private String emailSubject;

    private String emailMessage;

    private String contactId;

}
