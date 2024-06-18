package com.smartcontact.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.smartcontact.repositories.UserRepo;

@Service
public class SecurityCustomerUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // load our user
        return userRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found Exception" + username));

    }

}
