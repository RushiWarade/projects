package com.smartcontact.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.smartcontact.entities.ContactS;
import com.smartcontact.entities.User;
import com.smartcontact.forms.EmailForm;
import com.smartcontact.helpers.ResourceNotFoundException;
import com.smartcontact.repositories.ContactRepo;
import com.smartcontact.services.ContactService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepo contactRepo;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public ContactS save(ContactS contacts) {
        String contactId = UUID.randomUUID().toString();
        contacts.setId(contactId);
        return contactRepo.save(contacts);
    }

    @Override
    public ContactS update(ContactS contacts) {
        // System.out.println("contact id " + contacts.getId());

        ContactS contactS2 = contactRepo.getById(contacts.getId());
        if (contactS2 != null) {
            contactS2.setName(contacts.getName());
            contactS2.setEmail(contacts.getEmail());
            contactS2.setPhoneNumber(contacts.getPhoneNumber());
            contactS2.setDescription(contacts.getDescription());
            contactS2.setAddress(contacts.getAddress());
            contactS2.setLinkdinLink(contacts.getLinkdinLink());
            contactS2.setWebsiteLink(contacts.getWebsiteLink());
            contactS2.setFavorite(contacts.isFavorite());
            contactS2.setPicture(contacts.getPicture());
            contactS2.setCloudinaryImagePublicId(contacts.getCloudinaryImagePublicId());

        } else {
            new ResourceNotFoundException("User not found");
        }
        return contactRepo.save(contactS2);
    }

    @Override
    public List<ContactS> getAll() {
        return contactRepo.findAll();
    }

    @Override
    public ContactS getById(String id) {
        return contactRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with given id"));
    }

    @Override
    public void delete(String id) {

        var contact = contactRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with given id"));

        contactRepo.delete(contact);

    }

    @Override
    public List<ContactS> search(String name, String email, String phoneNumber) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'search'");
    }

    @Override
    public Page<ContactS> getByUser(User user, int page, int size, String sortBy, String direction) {

        Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        var pageable = PageRequest.of(page, size, sort);

        return contactRepo.findByUser(user, pageable);

    }
    // @Override
    // public List<ContactS> getByUserId(String userId) {
    // return contactRepo.findByUserId(userId);
    // }

    @Override
    public Page<ContactS> searchByNameContain(User user, String name, int page, int size, String sortBy,
            String direction) {
        Sort sort = sortBy.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByUserAndNameContaining(user, name, pageable);
    }

    @Override
    public Page<ContactS> searchByEmailContain(User user, String email, int page, int size, String sortBy,
            String direction) {
        Sort sort = sortBy.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByUserAndEmailContaining(user, email, pageable);
    }

    @Override
    public Page<ContactS> searchByPhoneNumberContain(User user, String phoneNumber, int page, int size, String sortBy,
            String direction) {
        Sort sort = sortBy.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByUserAndPhoneNumberContaining(user, phoneNumber, pageable);
    }

    @Override
    public void sendEmail(EmailForm emailForm) {

        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(emailForm.getEmailTo());
            helper.setSubject(emailForm.getEmailSubject());
            helper.setText(emailForm.getEmailMessage());
            helper.setFrom(emailForm.getEmailFrom());
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean existById(String id) {
        return contactRepo.existsById(id);
    }

    @Override
    public List<ContactS> LoginUserContacts(User user) {
       return contactRepo.findByUser(user);
    }

}
