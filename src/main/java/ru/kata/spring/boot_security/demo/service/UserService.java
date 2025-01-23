package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService extends UserDetailsService {
    List<User> getUsers();

    Optional<User> findByUsername(String username);

    List<User> getUsers(Integer limit);

    Optional<User> getUser(Long id);

    void createUser(User user);

    void updateUser(Long id, String username, String password, String name,
                    String email, Set<Role> roles);

    void deleteUser(Long id);

    void deleteAllUsers();
}
