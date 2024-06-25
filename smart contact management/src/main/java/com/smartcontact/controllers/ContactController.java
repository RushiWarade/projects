package com.smartcontact.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smartcontact.entities.ContactS;
import com.smartcontact.entities.User;
import com.smartcontact.forms.ContactForm;
import com.smartcontact.helpers.Helper;
import com.smartcontact.helpers.Message;
import com.smartcontact.helpers.MessageType;
import com.smartcontact.services.ContactService;
import com.smartcontact.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@RequestMapping("/user/contact")
@Controller
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    // add contact page
    @GetMapping("/add-contact")
    public String addContactView(Model model) {
        ContactForm contactForm = new ContactForm();
        model.addAttribute("contactForm", contactForm);

        return "user/add_contact";
    }

    @PostMapping("/add_contact")
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult bindingResult,
            Authentication authentication, HttpSession session) {
        if (bindingResult.hasErrors()) {
            Message message = Message.builder()
                    .content("Please required following fields..")
                    .type(MessageType.red)
                    .icon("fa-circle-info")
                    .build();

            session.setAttribute("message", message);
            return "user/add_contact";

        }

        try {

            String username = Helper.getEmailLoggedInUser(authentication);

            User user = userService.getUserByEmail(username);

            ContactS contactS = new ContactS();

            contactS.setName(contactForm.getName());
            contactS.setEmail(contactForm.getEmail());
            contactS.setPhoneNumber(contactForm.getPhoneNumber());
            contactS.setAddress(contactForm.getAddress());
            contactS.setDescription(contactForm.getDescription());
            contactS.setWebsiteLink(contactForm.getWebsiteLink());
            contactS.setLinkdinLink(contactForm.getLinkdinLink());
            contactS.setFavorite(contactForm.isFavorite());
            contactS.setUser(user);

            // System.out.println(contactForm);

            ContactS save = contactService.save(contactS);
            if (save != null) {
                Message message = Message.builder()
                        .content("Contact added successfully!")
                        .type(MessageType.green)
                        .icon("fa-thumbs-up")
                        .build();

                session.setAttribute("message", message);
            } else {
                Message message = Message.builder()
                        .content("Failed to add contact")
                        .type(MessageType.red)
                        .icon("fa-triangle-exclamation")
                        .build();
                session.setAttribute("message", message);
            }
        } catch (Exception e) {
            Message message = Message.builder()
                    .content("An error occurred while adding the contact")
                    .type(MessageType.red)
                    .icon("fa-triangle-exclamation")
                    .build();
            session.setAttribute("message", message);
            return "redirect:/user/contact/add-contact";
        }

        return "redirect:/user/contact/add-contact";
    }

}
