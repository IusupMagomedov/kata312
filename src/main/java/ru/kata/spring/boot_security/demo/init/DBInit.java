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

        User mario = new User();
        mario.setUsername("mario");
        mario.setPassword("mario");
        mario.setName("Mario");
        mario.setLastName("Rossi");
        mario.setAge(35);
        mario.setEmail("mario@gmail.com");
        mario.setRoles(new HashSet<>(Arrays.asList(user, admin)));

        User luigi = new User();
        luigi.setUsername("luigi");
        luigi.setPassword("luigi");
        luigi.setName("Luigi");
        luigi.setLastName("Verdi");
        luigi.setAge(30);
        luigi.setEmail("luigi@gmail.com");
        luigi.setRoles(new HashSet<>(Collections.singleton(user)));

        User peach = new User();
        peach.setUsername("peach");
        peach.setPassword("peach");
        peach.setName("Peach");
        peach.setLastName("Bianchi");
        peach.setAge(28);
        peach.setEmail("peach@gmail.com");
        peach.setRoles(new HashSet<>(Collections.singleton(user)));

        User toad = new User();
        toad.setUsername("toad");
        toad.setPassword("toad");
        toad.setName("Toad");
        toad.setLastName("Fungi");
        toad.setAge(25);
        toad.setEmail("toad@gmail.com");
        toad.setRoles(new HashSet<>(Collections.singleton(user)));

        User bowser = new User();
        bowser.setUsername("bowser");
        bowser.setPassword("bowser");
        bowser.setName("Bowser");
        bowser.setLastName("Koopa");
        bowser.setAge(40);
        bowser.setEmail("bowser@gmail.com");
        bowser.setRoles(new HashSet<>(Collections.singleton(user)));

        User birdo = new User();
        birdo.setUsername("birdo");
        birdo.setPassword("birdo");
        birdo.setName("Birdo");
        birdo.setLastName("Pink");
        birdo.setAge(29);
        birdo.setEmail("birdo@gmail.com");
        birdo.setRoles(new HashSet<>(Collections.singleton(user)));


        users.add(mario);
        users.add(luigi);
        users.add(peach);
        users.add(toad);
        users.add(bowser);
        users.add(birdo);
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
