package ru.kata.spring.boot_security.demo.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserApiController {
    private final UserService userService;

    @GetMapping
    public User getUser(@AuthenticationPrincipal User user) {
        return userService.getUser(user.getId()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
