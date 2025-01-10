package ru.kata.spring.boot_security.demo.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.spring.boot_security.demo.models.User;

public interface UserDao extends JpaRepository<User, Long> {
    List<User> findAll();

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    Long save(User user);

    void update(User user);

    void delete(Long user);
}
