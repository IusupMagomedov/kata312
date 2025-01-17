package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
public class HomeController {
    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getHomePage(Model model, @AuthenticationPrincipal UserDetails authenticatedUser) {
        if(authenticatedUser == null) {
            model.addAttribute("isAuthenticated", false);
        } else {
            model.addAttribute("isAuthenticated", true);
            User user = userService.findByUsername(authenticatedUser.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            model.addAttribute("user", user);  // Pass the full user object
        }
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
    public String getUserPage(Model model, @AuthenticationPrincipal UserDetails authenticatedUser) {
        User user = userService.findByUsername(authenticatedUser.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        model.addAttribute("user", user);
        model.addAttribute("userProperties", ObjectUtils.getObjectProperties(user));
        return "user";
    }
}