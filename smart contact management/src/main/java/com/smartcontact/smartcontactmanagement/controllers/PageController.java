package com.smartcontact.smartcontactmanagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.smartcontact.smartcontactmanagement.entities.User;
import com.smartcontact.smartcontactmanagement.forms.UserForms;
import com.smartcontact.smartcontactmanagement.services.UserService;

@Controller
public class PageController {

    @Autowired
    private UserService userService;

    // home route
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("name", "This is Rushi warade");
        model.addAttribute("email", "rushikeshwarade12@gmail.com");
        return "home";
    }

    // about route
    @GetMapping("/about")
    public String about(Model model) {
        return "about";
    }

    // services route
    @GetMapping("/services")
    public String services(Model model) {
        return "services";
    }

    // contact route
    @GetMapping("/contact")
    public String contact(Model model) {
        return "contact";
    }

    // login route
    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    // signup route
    @GetMapping("/signup")
    public String signup(Model model) {

        UserForms userForms = new UserForms();
        // userForms.setName("Rushi");

        model.addAttribute("userForm", userForms);

        return "register";
    }

    // processing register
    @PostMapping("/do-register")
    public String processRegister(@ModelAttribute UserForms userForms) {
        // save to database

        // userForm --> user
        User user = User.builder()
                .name(userForms.getName())
                .email(userForms.getEmail())
                .about(userForms.getAbout())
                .password(userForms.getPassword())
                .phoneNumber(userForms.getPhoneNumber())
.profilePic("https://commons.wikimedia.org/wiki/File:Windows_10_Default_Profile_Picture.svg")
                .build();


                System.out.println(user);
        userService.saveUser(user);
        System.out.println("User saved ");

        return "redirect:/signup";
    }

}