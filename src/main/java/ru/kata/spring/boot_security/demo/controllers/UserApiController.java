package ru.kata.spring.boot_security.demo.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserSessionService;

@RestController
@CrossOrigin
@AllArgsConstructor
public class UserApiController {
    private final UserService userService;
    private final UserSessionService userSessionService;
    @GetMapping("/api/user")
    public User getUser(@AuthenticationPrincipal UserDetails authenticatedUser) {
        return userSessionService.getAuthenticatedUser(authenticatedUser);
    }
}
