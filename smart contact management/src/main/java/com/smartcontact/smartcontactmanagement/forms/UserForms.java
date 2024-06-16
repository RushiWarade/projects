package com.smartcontact.smartcontactmanagement.forms;

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


    private String name;
    private String email;
    private String password;
    private String about;
    private String phoneNumber;
}
