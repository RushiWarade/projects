package com.smartcontact.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.smartcontact.entities.User;
import com.smartcontact.helpers.Helper;
import com.smartcontact.services.UserService;

@ControllerAdvice
public class RootController {

    @Autowired
    private UserService service;

    @ModelAttribute
    public void addLoggedInUserInfo(Model model, Authentication authentication) {

        if (authentication == null) {
            return;
        }

        String username = Helper.getEmailLoggedInUser(authentication);
        System.out.println(username);

        // fetch data using database

        User user = service.getEmailByEmail(username);
        System.out.println(user.getName());
        System.out.println(user.getEmail());

        model.addAttribute("loggedinUser", user);

    }

}
