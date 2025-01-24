package ru.kata.spring.boot_security.demo.dao;

import java.util.List;

import ru.kata.spring.boot_security.demo.models.User;

public interface UserDao {
    User findByUsername(String username);

    List<User> findAll();

    List<User> findTopN(Integer limit);

    User findById(Long id);

    User save(User user);

    void update(User user);

    void delete(Long user);

    void deleteAllUsers();
}
