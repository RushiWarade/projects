package com.smartcontact.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserForms {

    @NotBlank(message = "Username is required")
    @Size(min = 3, message = "Min 3 characters are required")
    private String name;

    @Email(message = "Invalid Email address")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Min 6 characters are required")
    private String password;

    @NotBlank(message = "About is required")
    private String about;

    @Size(min = 10, max = 13, message = "Phone number should be between 10 and 13 characters")
    private String phoneNumber;
}
