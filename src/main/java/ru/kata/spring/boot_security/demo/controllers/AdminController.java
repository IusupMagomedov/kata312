package ru.kata.spring.boot_security.demo.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDtoImpl;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserSessionService;
import ru.kata.spring.boot_security.demo.utils.UserMapper;

import java.util.*;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;
    private final UserMapper userMapper;
    private final UserSessionService userSessionService;

    @GetMapping
    public String getUsers(@RequestParam(value = "limit",
            required = false) Integer limit, Model model,
            @AuthenticationPrincipal UserDetails authenticatedUser,
            Authentication authentication) {
        List<User> users = userService.getUsers(limit);
        List<Role> roles = roleService.getRoles();

        model.addAttribute("users", users);
        model.addAttribute("roles", roles);

        model.addAttribute("isAuthenticated",
                userSessionService.isAuthenticated(authentication));
        model.addAttribute("isAdmin",
                userSessionService.isAdmin(authentication));
        model.addAttribute("user",
                userSessionService.getAuthenticatedUser(authenticatedUser));
        return "users";
    }

    @GetMapping("/create")
    public String getCreateUserPage(Model model,
            @AuthenticationPrincipal UserDetails authenticatedUser,
            Authentication authentication) {
        List<Role> roles = roleService.getRoles();

        model.addAttribute("roles", roles);
        model.addAttribute("isAuthenticated",
                userSessionService.isAuthenticated(authentication));
        model.addAttribute("isAdmin",
                userSessionService.isAdmin(authentication));
        model.addAttribute("user",
                userSessionService.getAuthenticatedUser(authenticatedUser));
        return "create";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute UserDtoImpl userDtoImpl) {
        User user = userMapper.toUser(userDtoImpl);
        userService.createUser(user);
        return "redirect:/admin";
    }


    @GetMapping("/update")
    public String updateUser(@RequestParam("id") Long id, Model model) {
        List<Role> roles = roleService.getRoles();
        User user = userService
                .getUser(id)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User not found")
                );
        model.addAttribute("roles", roles);
        model.addAttribute("user", user);
        return "update";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute UserDtoImpl userDtoImpl) {
        User user = userMapper.toUserWithId(userDtoImpl);
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}