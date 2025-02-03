package ru.kata.spring.boot_security.demo.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.dto.UserDtoImpl;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.utils.UserMapper;

import java.util.List;




@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/api/admin")
public class AdminApiController {
    private final UserService userService;
    private final RoleService roleService;
    private final UserMapper userMapper;

    @GetMapping("/health-check")
    public String getHealthCheck() {
        return "Situation Normal All Fired Up!";
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        userService.createUser(user);
        return user;
    }

    @PostMapping("/update")
    public ResponseEntity updateUser(@RequestBody UserDtoImpl userDto) {
        User user = userMapper.toUserWithId(userDto);
        userService.updateUser(user);
        return ResponseEntity.ok("User updated successfully");
    }

    @PostMapping("/delete")
    public void deleteUser(@RequestBody String id) {
        userService.deleteUser(Long.valueOf(id));
    }

    @GetMapping("/roles")
    public List<Role> getRoles() {
        return roleService.getRoles();
    }
}
