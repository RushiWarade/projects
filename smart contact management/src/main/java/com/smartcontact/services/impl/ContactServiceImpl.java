package com.smartcontact.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public Page<ContactS> searchByNameContain(User user, String name, int page, int size, String sortBy, String direction) {
        Sort sort = sortBy.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByUserAndNameContaining(user, name, pageable);
    }

    @Override
    public Page<ContactS> searchByEmailContain(User user, String email, int page, int size, String sortBy, String direction) {
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

}
