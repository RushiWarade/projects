package com.smartcontact.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.smartcontact.entities.User;
import com.smartcontact.forms.UserForms;
import com.smartcontact.helpers.Message;
import com.smartcontact.helpers.MessageType;
import com.smartcontact.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PageController {

    @Autowired
    private UserService userService;


    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }

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

    @PostMapping("/login")
    public String signupPost() {

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

    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForms userForm, BindingResult rBindingResult,

            HttpSession session) {

        // validate form data
        if (rBindingResult.hasErrors()) {
            return "register";
        }

        // save to database

        try {

            User user = new User();
            user.setName(userForm.getName());
            user.setEmail(userForm.getEmail());
            user.setPassword(userForm.getPassword());
            user.setAbout(userForm.getAbout());
            user.setPhoneNumber(userForm.getPhoneNumber());
            user.setProfilePic(
                    "https://www.learncodewithdurgesh.com/_next/image?url=%2F_next%2Fstatic%2Fmedia%2Fdurgesh_sir.35c6cb78.webp&w=1920&q=75");

            boolean userExist = userService.isUserExistByEmail(userForm.getEmail());
            // System.out.println(userExist + " check user exist or not");

            if (!userExist) {
                userService.saveUser(user);

                userService.varifyEmail(user);

                Message message = Message.builder()
                        .content("Registration Successful")
                        .icon("fa-thumbs-up")
                        .type(MessageType.green)
                        .build();

                session.setAttribute("message", message);
            } else {
                Message message = Message.builder()
                        .content(user.getEmail() + ", Email already exist!. ")
                        .icon("fa-triangle-exclamation")
                        .type(MessageType.red)
                        .build();

                session.setAttribute("message", message);
            }

            // add the message:
        } catch (

        Exception e) {
            Message message = Message.builder()
                    .content("An error occurred while registration!.")
                    .type(MessageType.red)
                    .icon("fa-triangle-exclamation")
                    .build();
            session.setAttribute("message", message);
            return "redirect:/signup";
        }

        // redirectto login page
        return "redirect:/signup";
    }

    @GetMapping("/varify/{userId}")
    public String varifyUserEmail(@PathVariable String userId, HttpSession session) {
        System.out.println(userId);

        User user = userService.getUserById(userId).orElse(null);

        if (user != null) {
            user.setEmailVerified(true);
            user.setEnabled(true);

            userService.updateUser(user);
            Message message = Message.builder()
                    .content("Your Account is Varified, Login Now..")
                    .type(MessageType.green)
                    .icon("fa-thumbs-up")
                    .build();
            session.setAttribute("message", message);
            return "redirect:/login";
        } else {

            Message message = Message.builder()
                    .content("Something wrong please resend varification Link!.")
                    .type(MessageType.red)
                    .icon("fa-triangle-exclamation")
                    .build();
            session.setAttribute("message", message);

            return "redirect:/login";
        }
    }

    @GetMapping("/re-send/{userId}")
    public String reSendEmail(@PathVariable String userId, HttpSession session) {

        // System.out.println(userId);
        User user = userService.getUserById(userId).orElse(null);

        if (user != null) {

            userService.varifyEmail(user);

            Message message = Message.builder()
                    .content("Email Resend")
                    .icon("fa-thumbs-up")
                    .type(MessageType.green)
                    .build();

            session.setAttribute("message", message);
            return "redirect:/login";
        }
        return "redirect:/login";
    }

}