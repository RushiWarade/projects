package com.smartcontact.helpers;

import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {

    public static String getEmailLoggedInUser(Authentication authentication) {

        if (authentication instanceof OAuth2AuthenticationToken) {

            var oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
            var clientId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

            var oauth2User = (OAuth2User) authentication.getPrincipal();

            String username = "";

            // gating data from gitgub
            if (clientId.equalsIgnoreCase("github")) {

                // fetch username in github
                username = oauth2User.getAttribute("email") != null
                ? oauth2User.getAttribute("email").toString()
                : oauth2User.getAttribute("login").toString() + "@github.com";

            } else if (clientId.equalsIgnoreCase("google")) {
                // implement this for google login

                // use in google case
                // fetch username in google
                // oauth2User.getAttribute("email").toString();
            }

            return username;

        } else {
            // getting data from local database
            return authentication.getName();
        }

    }
}
