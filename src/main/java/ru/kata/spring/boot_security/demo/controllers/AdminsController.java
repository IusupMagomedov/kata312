package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;


import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminsController {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminsController(UserService userService, RoleService roleService, @Qualifier("passwordEncoder") PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String getUsers(@RequestParam(value = "limit", required = false) Integer limit, Model model) {
        List<User> users = userService.getUsers(limit);
        List<Role> roles = roleService.getRoles();
        model.addAttribute("users", users);
        model.addAttribute("roles", roles);
        return "users";
    }

    @PostMapping("/create")
    public String addUser(Model model, @RequestParam("name") String name, @RequestParam("username") String username, @RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("roles") String[] roleIds, @AuthenticationPrincipal UserDetails authenticatedUser, Authentication authentication) {

        Set<Role> roles = Arrays.stream(roleIds).mapToLong(Long::parseLong).mapToObj(roleService::getRole).filter(Optional::isPresent).map(Optional::get).peek(System.out::println).collect(Collectors.toSet());
        userService.createUser(username, password, name, email, roles);
        if (authenticatedUser == null) {
            model.addAttribute("isAuthenticated", false);
        } else {
            model.addAttribute("isAuthenticated", true);
            User user = userService.findByUsername(authenticatedUser.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            Set<String> stringRoles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
            boolean isAdmin = stringRoles.contains("ROLE_ADMIN");
            model.addAttribute("isAdmin", isAdmin);
            model.addAttribute("user", user);
        }
        return "redirect:/admin";
    }

    @GetMapping("/update")
    public String updateUser(@RequestParam("id") Long id, Model model) {
        List<Role> roles = roleService.getRoles();
        User user = userService.getUser(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        model.addAttribute("roles", roles);
        model.addAttribute("user", user);
        return "update";
    }

    @PostMapping("/update")
    public String updateUser(@RequestParam("id") Long id, @RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("roles") String[] roleIds) {
        Set<Role> roles = Arrays.stream(roleIds).mapToLong(Long::parseLong).mapToObj(roleService::getRole).filter(Optional::isPresent).map(Optional::get).peek(System.out::println).collect(Collectors.toSet());
        userService.updateUser(id, username, password, name, email, roles);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
