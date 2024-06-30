package com.smartcontact.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.smartcontact.entities.ContactS;
import com.smartcontact.entities.User;

public interface ContactService {

    // save contact
    ContactS save(ContactS contactS);

    // update contact
    ContactS update(ContactS contactS);

    // get all contact
    List<ContactS> getAll();

    // get contact by id
    ContactS getById(String id);

    // delete contact
    void delete(String id);

    // search contact
    List<ContactS> search(String name, String email, String phoneNumber);

    // get contacts by userid list
    // List<ContactS> getByUserId(String userId);

    Page<ContactS> getByUser(User user, int page, int size, String sortBy, String direction);

    Page<ContactS> searchByNameContain(User user, String name, int page, int size, String sortBy, String direction);

    Page<ContactS> searchByEmailContain(User user, String email, int page, int size, String sortBy, String direction);

    Page<ContactS> searchByPhoneNumberContain(User user, String phoneNumber, int page, int size, String sortBy, String direction);

}
