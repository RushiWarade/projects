package com.smartcontact.controllers;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.smartcontact.entities.ContactS;
import com.smartcontact.entities.User;
import com.smartcontact.forms.ContactForm;
import com.smartcontact.helpers.AppConstaints;
import com.smartcontact.helpers.Helper;
import com.smartcontact.helpers.Message;
import com.smartcontact.helpers.MessageType;
import com.smartcontact.services.ContactService;
import com.smartcontact.services.UserService;
import com.smartcontact.services.ImageServices;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RequestMapping("/user/contact")
@Controller
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageServices imageServices;

    @Autowired
    private Cloudinary cloudinary;

    private Logger logger = LoggerFactory.getLogger(ContactController.class);

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

        // check validation
        if (bindingResult.hasErrors()) {
            Message message = Message.builder()
                    .content("Please required following fields..")
                    .type(MessageType.red)
                    .icon("fa-circle-info")
                    .build();

            session.setAttribute("message", message);
            return "user/add_contact";
        }

        logger.info(contactForm.getPicture().getOriginalFilename());
        String filename = UUID.randomUUID().toString();

        String fileUrl = null;

        try {

            String username = Helper.getEmailLoggedInUser(authentication);

            if (!contactForm.getPicture().isEmpty()) {
                // image upload code
                fileUrl = imageServices.uploadImage(contactForm.getPicture(), filename);

            }

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
            contactS.setPicture(fileUrl);
            contactS.setCloudinaryImagePublicId(filename);

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

    @GetMapping
    public String showAllContacts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "3") int size,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            Model model,
            Authentication authentication) {

        String username = Helper.getEmailLoggedInUser(authentication);

        User user = userService.getUserByEmail(username);

        Page<ContactS> pageContacts = contactService.getByUser(user, page, size, sortBy, direction);

        // Debugging: Print pageContact content to console
        // pageContacts.getContent().forEach(contact ->
        // System.out.println(contact.getName() + " " + contact.getEmail()));

        // List<ContactS> contactS = pageContacts.getContent();
        // System.out.println(pageContacts.getNumber());

        // contactS.forEach(contacts -> System.out.println(contacts.getName()));
        // System.out.println("page size " + AppConstaints.PAGE_SIZE);

        model.addAttribute("pageContent", pageContacts);
        model.addAttribute("pageSize", AppConstaints.PAGE_SIZE);

        return "user/contacts";
    }

    @GetMapping("/search")
    public String search(@RequestParam("field") String field,
            @RequestParam("keyword") String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "3") int size,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            Model model,
            Authentication authentication) {

        User user = userService.getUserByEmail(Helper.getEmailLoggedInUser(authentication));
        // String user = Helper.getEmailLoggedInUser(authentication);
        Page<ContactS> contactSPage = null;

        if (field.equals("name")) {
            System.out.println("search using name");
            contactSPage = contactService.searchByNameContain(user, keyword, page, size, sortBy, direction);
        } else if (field.equals("email")) {
            System.out.println("search using email");

            contactSPage = contactService.searchByEmailContain(user, keyword, page, size, sortBy, direction);

        } else if (field.equals("phoneNumber")) {
            // System.out.println("search using number");
            // System.out.println(keyword);
            // System.out.println(field);

            contactSPage = contactService.searchByPhoneNumberContain(user, keyword, page, size, sortBy, direction);
        }

        model.addAttribute("pageContent", contactSPage);
        model.addAttribute("pageSize", AppConstaints.PAGE_SIZE);

        return "user/contacts";
    }

    @GetMapping("/delete/{contactId}")
    public String deleteContactById(@PathVariable String contactId, HttpSession session) throws IOException {
        // System.out.println(contactId);

        ContactS contactS = contactService.getById(contactId);

        // delete the contact imagein cloudinary
        if (contactS.getCloudinaryImagePublicId() != null) {
            cloudinary.uploader().destroy(contactS.getCloudinaryImagePublicId(), ObjectUtils.emptyMap());
        }

        contactService.delete(contactId);
        Message message = Message.builder()
                .content("Contact Deleted successfully!")
                .type(MessageType.green)
                .icon("fa-thumbs-up")
                .build();

        session.setAttribute("message", message);

        return "redirect:/user/contact";
    }

    @GetMapping("/update/{contactId}")
    public String updateContactView(@PathVariable String contactId, Model model, HttpSession httpSession) {

        ContactS contact = contactService.getById(contactId);

        ContactForm contactForm = new ContactForm();
        contactForm.setName(contact.getName());
        contactForm.setEmail(contact.getEmail());
        contactForm.setPhoneNumber(contact.getPhoneNumber());
        contactForm.setDescription(contact.getDescription());
        contactForm.setAddress(contact.getAddress());
        contactForm.setContactImage(contact.getPicture());
        contactForm.setLinkdinLink(contact.getLinkdinLink());
        contactForm.setWebsiteLink(contact.getWebsiteLink());
        contactForm.setFavorite(contact.isFavorite());
        contactForm.setId(contact.getId());

        model.addAttribute("contactForm", contactForm);

        return "user/update_contact";
    }

    @PostMapping("/update")
    public String updateContactPrecess(@ModelAttribute ContactForm contactForm, HttpSession session) {

        ContactS contactS = new ContactS();

        contactS.setName(contactForm.getName());
        contactS.setEmail(contactForm.getEmail());
        contactS.setPhoneNumber(contactForm.getPhoneNumber());
        contactS.setDescription(contactForm.getDescription());
        contactS.setAddress(contactForm.getAddress());
        contactS.setLinkdinLink(contactForm.getLinkdinLink());
        contactS.setWebsiteLink(contactForm.getWebsiteLink());
        contactS.setFavorite(contactForm.isFavorite());
        contactS.setId(contactForm.getId());
        // contactS.setp

        contactService.update(contactS);
        System.out.println("contact form id check " + contactForm.getId());

        Message message = Message.builder()
                .content("Contact Updated successfully!")
                .type(MessageType.green)
                .icon("fa-thumbs-up")
                .build();

        session.setAttribute("message", message);

        return "redirect:/user/contact";
    }

}
