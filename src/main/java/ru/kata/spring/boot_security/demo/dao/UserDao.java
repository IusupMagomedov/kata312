package ru.kata.spring.boot_security.demo.dao;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.GrantedAuthority;
import ru.kata.spring.boot_security.demo.models.User;

public interface UserDao {
    User findByUsername(String username);

    List<User> findAll();

    User findById(Long id);

    User save(User user);

    void update(User user);

    void delete(Long user);
}
