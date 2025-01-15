package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }


    @Override
    public List<User> getUsers() {
        return userDao.findAll();
    }

    @Override
    public List<User> getUsers(Integer limit) {
//        if (limit != null) {
//            return userDao.findAll().stream().limit(limit).toList();
//        }
        return userDao.findAll();
    }

    @Override
    public Optional<User> getUser(Long id) {
        return Optional.ofNullable(userDao.findById(id)); //findById(id);
    }

    @Transactional
    @Override
    public void createUser(String name, String username, String email, String password) {
        User user = new User(name, username, password, email);
        User savedUser = userDao.save(user);
        System.out.println("User has been created, id: " + savedUser.getId());
    }

    @Transactional
    @Override
    public void updateUser(Long id, String name, String username, String email, String password) {
        User user = userDao.findById(id);
        user.setName(name);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        userDao.update(user);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        userDao.delete(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.findByUsername(username);
    }
}
