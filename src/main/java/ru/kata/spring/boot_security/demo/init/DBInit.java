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
        Role user = roleService.findByName("ROLE_USER").get();
        Role admin = roleService.findByName("ROLE_ADMIN").get();
        User mario = new User();
        mario.setUsername("mario");
        mario.setPassword("mario");
        mario.setName("Mario");
        mario.setEmail("mario@gmail.com");
        mario.setRoles(new HashSet<>(Arrays.asList(user, admin)));

        User luigi = new User();
        luigi.setUsername("luigi");
        luigi.setPassword("luigi");
        luigi.setName("Luigi");
        luigi.setEmail("luigi@gmail.com");
        luigi.setRoles(new HashSet<>(Collections.singleton(user)));

        User peach = new User();
        peach.setUsername("peach");
        peach.setPassword("peach");
        peach.setName("Peach");
        peach.setEmail("peach@gmail.com");
        peach.setRoles(new HashSet<>(Collections.singleton(user)));

        User toad = new User();
        toad.setUsername("toad");
        toad.setPassword("toad");
        toad.setName("Toad");
        toad.setEmail("toad@gmail.com");
        toad.setRoles(new HashSet<>(Collections.singleton(user)));

        User bowser = new User();
        bowser.setUsername("bowser");
        bowser.setPassword("bowser");
        bowser.setName("Bowser");
        bowser.setEmail("bowser@gmail.com");
        bowser.setRoles(new HashSet<>(Collections.singleton(user)));

        users.add(mario);
        users.add(luigi);
        users.add(peach);
        users.add(toad);
        users.add(bowser);
    }

    @PostConstruct
    private void postConstruct() {
        setRoles();
        roles.forEach(role -> roleService.createRole(role.getName()));

        setUsers();
        users.forEach(user -> userService.createUser(
                user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getEmail(),
                user.getRoles()
        ));
    }

    @PreDestroy
    private void preDestroy() {
        userService.deleteAllUsers();
        roleService.deleteAllRoles();
    }
}
