package com.smartcontact.forms;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;

// import org.hibernate.validator.constraints.Email;
// import org.hibernate.validator.constraints.NotBlank;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactForm {

    private String id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Eamil is required")
    @Email
    private String email;

    @NotBlank(message = "Mobile number is required")
    @Size(max = 13, message = "max 13 digit is allowed")
    private String phoneNumber;

    @NotBlank(message = "address is required")
    private String address;

    private String description;

    private boolean favorite;

    private String websiteLink;

    private String linkdinLink;

    
    private MultipartFile picture;

    private String contactImage;
}
