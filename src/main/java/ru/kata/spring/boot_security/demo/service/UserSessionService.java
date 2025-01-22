package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import ru.kata.spring.boot_security.demo.controllers.ObjectUtils;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface UserSessionService {
    Optional<User> getAuthenticatedUser(UserDetails authenticatedUser);

    Map<String, Object> getUserProperties(UserDetails authenticatedUser);

    boolean isAdmin(Authentication authentication);

    Set<String> getRoles(Authentication authentication);

    boolean isAuthenticated(Authentication authentication);
}
