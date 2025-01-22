package ru.kata.spring.boot_security.demo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;

@Repository
@AllArgsConstructor
public class RoleDaoImpl implements RoleDao {
    private final EntityManager entityManager;

    @Override
    public Role findByName(String name) {
        TypedQuery<Role> query = entityManager
                .createQuery("SELECT r FROM Role r WHERE r.name = :name",
                        Role.class);
        query.setParameter("name", name);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Role> findAll() {
        return entityManager
                .createQuery("SELECT u FROM Role u", Role.class)
                .getResultList();
    }

    @Override
    public Role findById(Long id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    public Role save(Role role) {
        entityManager.persist(role);
        return role;
    }

    @Override
    public void update(Role role) {
        entityManager.merge(role);
    }

    @Override
    public void delete(Long id) {
        Role role = entityManager.find(Role.class, id);
        entityManager.remove(role);
    }

    @Override
    public void deleteAllRoles() {
        entityManager.createQuery("DELETE FROM Role r").executeUpdate();
    }
}
