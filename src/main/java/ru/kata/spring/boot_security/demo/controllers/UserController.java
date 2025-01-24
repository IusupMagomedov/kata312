package ru.kata.spring.boot_security.demo.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserSessionService;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserSessionService userSessionService;
    @GetMapping
    public String getUserPage(Model model,
                              @AuthenticationPrincipal
                              UserDetails authenticatedUser,
                              Authentication authentication) {
        model.addAttribute("isAuthenticated",
                userSessionService.isAuthenticated(authentication));
        model.addAttribute("isAdmin",
                userSessionService.isAdmin(authentication));
        model.addAttribute("user",
                userSessionService.getAuthenticatedUser(authenticatedUser));
        return "user";
    }
}