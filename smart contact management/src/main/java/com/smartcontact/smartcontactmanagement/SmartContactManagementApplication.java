package com.smartcontact.smartcontactmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.smartContact"})
public class SmartContactManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartContactManagementApplication.class, args);
    }

}
