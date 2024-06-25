package com.smartcontact.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartcontact.entities.ContactS;
import com.smartcontact.entities.User;

@Repository
public interface ContactRepo extends JpaRepository<ContactS, String> {

    // find the contacts of user

    // custom finder method
    List<ContactS> findByUser(User user);

    // custom finder query
    // @Query("SELECT C FROM ContactS where c.userId = :userId")
    // List<ContactS> findByUserId(@Param("userId") String userId);

}
