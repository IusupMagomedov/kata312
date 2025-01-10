package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    List<User> getUsers();

    List<User> getUsers(Integer limit);

    Optional<User> getUser(Long id);

    void createUser(String name, String username, String email, String password);

    void updateUser(Long id, String name, String username, String email, String password);

    void deleteUser(Long id);
}
