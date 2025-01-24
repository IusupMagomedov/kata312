package ru.kata.spring.boot_security.demo.dao;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import ru.kata.spring.boot_security.demo.models.User;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserDaoImpl implements UserDao {
    private final EntityManager entityManager;

    @Override
    public Optional<User> findByUsername(String username) {
        TypedQuery<User> query = entityManager
                .createQuery("SELECT u FROM User u " +
                                "WHERE u.username = :username",
                        User.class);
        query.setParameter("username", username);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        return entityManager
                .createQuery("SELECT u FROM User u", User.class)
                .getResultList();
    }

    @Override
    public List<User> findTopN(Integer limit) {
        TypedQuery<User> query = entityManager
                .createQuery("FROM User u", User.class);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User save(User user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    public void delete(Long id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }

    @Override
    public void deleteAllUsers() {
        entityManager
                .createQuery("DELETE FROM User u")
                .executeUpdate();
    }
}