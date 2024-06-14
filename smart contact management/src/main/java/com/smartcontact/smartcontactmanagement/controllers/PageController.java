package com.smartcontact.smartcontactmanagement.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

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
        return "register";
    }

}