package ru.kata.spring.boot_security.demo.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@RestController
@CrossOrigin
@AllArgsConstructor
public class UserApiController {
    private final UserService userService;

    @GetMapping("/api/user")
    public User getUser(@RequestParam String id) {
        User user = userService.getUser(Long.valueOf(id)).orElseThrow(() -> new UsernameNotFoundException(id));
        return user;
    }
}
