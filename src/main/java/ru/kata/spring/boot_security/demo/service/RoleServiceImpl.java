package ru.kata.spring.boot_security.demo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleDao roleDao;

    @Override
    public List<Role> getRoles() {
        return roleDao.findAll();
    }

    @Override
    public Optional<Role> findByName(String name) {
        return roleDao.findByName(name);
    }

    @Override
    public Optional<Role> getRole(Long id) {
        return Optional.ofNullable(roleDao.findById(id));
    }

    @Transactional
    @Override
    public Role createRole(String name) {
        Role role = new Role();
        role.setName(name);
        Role savedRole = roleDao.save(role);
        System.out.println("Role has been created, id: " + savedRole.getId());
        return savedRole;
    }

    @Transactional
    @Override
    public void updateRole(Long id, String name) {
        Role role = roleDao.findById(id);
        role.setName(name);
        roleDao.update(role);
    }

    @Transactional
    @Override
    public void deleteRole(Long id) {
        roleDao.delete(id);
    }

    @Transactional
    @Override
    public void deleteAllRoles() {
        roleDao.deleteAllRoles();
    }
}