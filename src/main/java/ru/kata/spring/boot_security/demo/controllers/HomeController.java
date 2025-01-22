package ru.kata.spring.boot_security.demo.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import ru.kata.spring.boot_security.demo.service.UserSessionService;

@Controller
@AllArgsConstructor
public class HomeController {
    private final UserSessionService userSessionService;

    @GetMapping("/")
    public String getHomePage(Model model,
                              @AuthenticationPrincipal UserDetails authenticatedUser,
                              Authentication authentication) {
        model.addAttribute("isAuthenticated",
                userSessionService.isAuthenticated(authentication));
        model.addAttribute("isAdmin",
                userSessionService.isAdmin(authentication));
        model.addAttribute("user",
                userSessionService.getAuthenticatedUser(authenticatedUser));
        return "home";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        return "login";
    }

    @GetMapping("/logout")
    public String getLogoutPage(Model model) {
        return "logout";
    }

    @GetMapping("/user")
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
        model.addAttribute("userProperties",
                userSessionService.getUserProperties(authenticatedUser));
        return "user";
    }
}