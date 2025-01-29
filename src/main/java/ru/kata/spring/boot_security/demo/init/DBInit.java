package ru.kata.spring.boot_security.demo.init;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

@Component
public class DBInit {
    private final UserService userService;
    private final RoleService roleService;
    private final ArrayList<User> users = new ArrayList<>();
    private final ArrayList<Role> roles = new ArrayList<>();

    @Autowired
    public DBInit(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    private void setRoles() {
        Role admin = new Role();
        admin.setName("ROLE_ADMIN");
        Role user = new Role();
        user.setName("ROLE_USER");

        roles.add(admin);
        roles.add(user);
    }

    private void setUsers() {
        Role user = roleService.findByName("ROLE_USER").orElseThrow(
                () -> new RuntimeException("Role not found")
        );
        Role admin = roleService.findByName("ROLE_ADMIN").orElseThrow(
                () -> new RuntimeException("Role not found")
        );

        User mario = User.builder()
                .username("mario")
                .password("mario")
                .name("Mario")
                .lastName("Rossi")
                .age(35)
                .email("mario@gmail.com")
                .roles(new HashSet<>(Arrays.asList(user, admin)))
                .build();

        User luigi = User.builder()
                .username("luigi")
                .password("luigi")
                .name("Luigi")
                .lastName("Verdi")
                .age(30)
                .email("luigi@gmail.com")
                .roles(new HashSet<>(Collections.singleton(user)))
                .build();

        User peach = User.builder()
                .username("peach")
                .password("peach")
                .name("Peach")
                .lastName("Bianchi")
                .age(28)
                .email("peach@gmail.com")
                .roles(new HashSet<>(Collections.singleton(user)))
                .build();

        User toad = User.builder()
                .username("toad")
                .password("toad")
                .name("Toad")
                .lastName("Fungi")
                .age(25)
                .email("toad@gmail.com")
                .roles(new HashSet<>(Collections.singleton(user)))
                .build();

        User bowser = User.builder()
                .username("bowser")
                .password("bowser")
                .name("Bowser")
                .lastName("Koopa")
                .age(40)
                .email("bowser@gmail.com")
                .roles(new HashSet<>(Collections.singleton(user)))
                .build();

        User birdo = User.builder()
                .username("birdo")
                .password("birdo")
                .name("Birdo")
                .lastName("Pink")
                .age(29)
                .email("birdo@gmail.com")
                .roles(new HashSet<>(Collections.singleton(user)))
                .build();

        users.addAll(Arrays.asList(mario, luigi, peach, toad, bowser, birdo));
    }

    @PostConstruct
    private void postConstruct() {
        setRoles();
        roles.forEach(role -> roleService.createRole(role.getName()));

        setUsers();
        users.forEach(userService::createUser);
    }

    @PreDestroy
    private void preDestroy() {
        userService.deleteAllUsers();
        roleService.deleteAllRoles();
    }
}
