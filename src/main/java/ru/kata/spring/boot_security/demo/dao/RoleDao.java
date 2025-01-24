package ru.kata.spring.boot_security.demo.dao;


import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;
import java.util.Optional;

public interface RoleDao {

    Optional<Role> findByName(String name);

    List<Role> findAll();

    Role findById(Long id);

    Role save(Role role);

    void update(Role role);

    void delete(Long role);

    void deleteAllRoles();
}
