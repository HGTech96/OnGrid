package com.hg.dashboard.security;

import com.hg.dashboard.domain.User;
import com.hg.dashboard.exception.UserNotFoundException;
import com.hg.dashboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AuthenticationFacade {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public Long getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userDetails = (User) authentication.getPrincipal();
        return userDetails.getId();
    }

    public User getAuthenticatedUser() {
        try {
            return userService.getUser(getAuthenticatedUserId());
        } catch (UserNotFoundException ex) {
            throw new InsufficientAuthenticationException("Cannot fetch authenticated user");
        }
    }
}