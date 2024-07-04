package com.smartcontact.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartcontact.entities.ContactS;
import com.smartcontact.entities.User;

@Repository
public interface ContactRepo extends JpaRepository<ContactS, String> {

    // find the contacts of user

    // custom finder method
    Page<ContactS> findByUser(User user, Pageable pageable);

    List<ContactS> findByUser(User user);

    // custom finder query
    // @Query("SELECT C FROM ContactS where c.userId = :userId")
    // List<ContactS> findByUserId(@Param("userId") String userId);

    Page<ContactS> findByUserAndNameContaining(User user ,String name, Pageable pageable);

    Page<ContactS> findByUserAndEmailContaining(User user ,String name, Pageable pageable);

    Page<ContactS> findByUserAndPhoneNumberContaining(User user ,String name, Pageable pageable);

}
