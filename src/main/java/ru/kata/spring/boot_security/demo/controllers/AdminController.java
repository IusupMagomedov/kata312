package ru.kata.spring.boot_security.demo.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDtoImpl;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @GetMapping
    public String getUsers(@RequestParam(value = "limit",
            required = false) Integer limit, Model model) {
        List<User> users = userService.getUsers(limit);
        List<Role> roles = roleService.getRoles();
        model.addAttribute("users", users);
        model.addAttribute("roles", roles);
        return "users";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute UserDtoImpl userDtoImpl) {
        Set<Role> roles = Arrays
                .stream(userDtoImpl.getRoles())
                .mapToLong(Long::parseLong)
                .mapToObj(roleService::getRole)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .peek(System.out::println)
                .collect(Collectors.toSet());

        User user = new User();
        user.setUsername(userDtoImpl.getUsername());
        user.setPassword(userDtoImpl.getPassword());
        user.setName(userDtoImpl.getName());
        user.setEmail(userDtoImpl.getEmail());
        user.setRoles(roles);

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
        Set<Role> roles = Arrays
                .stream(userDtoImpl.getRoles())
                .mapToLong(Long::parseLong)
                .mapToObj(roleService::getRole)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .peek(System.out::println)
                .collect(Collectors.toSet());

        User user = new User();
        user.setId(Long.parseLong(userDtoImpl.getId()));
        user.setUsername(userDtoImpl.getUsername());
        user.setPassword(userDtoImpl.getPassword());
        user.setName(userDtoImpl.getName());
        user.setEmail(userDtoImpl.getEmail());
        user.setRoles(roles);

        userService.updateUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
