package com.smartcontact.config;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.smartcontact.entities.User;
import com.smartcontact.helpers.AppConstaints;
import com.smartcontact.helpers.Message;
import com.smartcontact.helpers.MessageType;
import com.smartcontact.repositories.UserRepo;
import com.smartcontact.services.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AuthenticateFailwareHandler implements AuthenticationFailureHandler {

    @Autowired
    private UserRepo repo;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

        HttpSession session = request.getSession();

        String email = request.getParameter("email");

        User user = repo.findByEmail(email).orElse(null);

        if (exception instanceof DisabledException) {

            System.out.println(user.getUserId());

            String resendLink = AppConstaints.BASE_URL+"/re-send/" + user.getUserId();
            String messageContent = "This user is disabled. Check your email and verify! <br>" +
                    "<a target='_blank' href='" + resendLink
                    + "' class='text-green bg-green-600 p-1.5 rounded-lg leading-8'>Resend verification email</a>";

            session.setAttribute("message", Message.builder()
                    .content(messageContent)
                    .type(MessageType.red)
                    .icon("fa-triangle-exclamation")
                    .build());
            response.sendRedirect("/login");
        } else {
            response.sendRedirect("/login?error=true");
        }
    }

}
