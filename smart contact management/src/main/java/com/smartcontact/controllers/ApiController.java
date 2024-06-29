package com.smartcontact.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartcontact.entities.ContactS;
import com.smartcontact.services.ContactService;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private ContactService contactService;

    @GetMapping("/contact/{contactId}")
    public ContactS getContact(@PathVariable String contactId) {
        System.out.println("mobile screen click");
        System.out.println(contactId);
        return contactService.getById(contactId);
    }

}
