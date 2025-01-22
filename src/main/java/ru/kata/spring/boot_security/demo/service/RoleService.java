package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.models.Role;
import java.util.List;
import java.util.Optional;

public interface RoleService {

    List<Role> getRoles();

    Optional<Role> findByName(String name);

    Optional<Role> getRole(Long id);

    Role createRole(String name);

    void updateRole(Long id, String name);

    void deleteRole(Long id);

    void deleteAllRoles();
}