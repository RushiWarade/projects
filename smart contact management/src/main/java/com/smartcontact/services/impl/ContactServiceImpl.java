package com.smartcontact.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartcontact.entities.ContactS;
import com.smartcontact.entities.User;
import com.smartcontact.helpers.ResourceNotFoundException;
import com.smartcontact.repositories.ContactRepo;
import com.smartcontact.services.ContactService;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepo contactRepo;

    @Override
    public ContactS save(ContactS contacts) {
        String contactId = UUID.randomUUID().toString();
        contacts.setId(contactId);
        return contactRepo.save(contacts);
    }

    @Override
    public ContactS update(ContactS contacts) {

        return contactRepo.save(contacts);
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
    public List<ContactS> getByUser(User user) {
        return contactRepo.findByUser(user);
    }

    // @Override
    // public List<ContactS> getByUserId(String userId) {
    // return contactRepo.findByUserId(userId);
    // }

}
