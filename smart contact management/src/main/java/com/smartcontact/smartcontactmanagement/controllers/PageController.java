package com.smartcontact.smartcontactmanagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.smartcontact.smartcontactmanagement.entities.User;
import com.smartcontact.smartcontactmanagement.forms.UserForms;
import com.smartcontact.smartcontactmanagement.helpers.Message;
import com.smartcontact.smartcontactmanagement.helpers.MessageType;
import com.smartcontact.smartcontactmanagement.services.UserService;

import jakarta.servlet.http.HttpSession;

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
    public String processRegister(@ModelAttribute UserForms userForms, HttpSession session) {
        // save to database

        // userForm --> user
        // User user = User.builder()
        // .name(userForms.getName())
        // .email(userForms.getEmail())
        // .about(userForms.getAbout())
        // .password(userForms.getPassword())
        // .phoneNumber(userForms.getPhoneNumber())
        // .profilePic("https://commons.wikimedia.org/wiki/File:Windows_10_Default_Profile_Picture.svg")
        // .build();

        try {

            User user = new User();
            user.setName(userForms.getName());
            user.setEmail(userForms.getEmail());
            user.setAbout(userForms.getAbout());
            user.setPassword(userForms.getPassword());
            user.setPhoneNumber(userForms.getPhoneNumber());
            user.setProfilePic("https://commons.wikimedia.org/wiki/File:Windows_10_Default_Profile_Picture.svg");

            // System.out.println(user);
            userService.saveUser(user);
            // System.out.println("User saved ");

            Message message = Message.builder().content("Registration Successfull").type(MessageType.green).build();

            session.setAttribute("message", message);

            return "redirect:/signup";
        } catch (Exception e) {
            Message message = Message.builder().content("Something went wrong , Please try again").type(MessageType.red).build();

            session.setAttribute("message", message);

            return "redirect:/signup";

        }

    }

}