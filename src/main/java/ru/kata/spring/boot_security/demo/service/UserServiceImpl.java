package ru.kata.spring.boot_security.demo.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> getUsers() {
        return userDao.findAll();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(userDao.findByUsername(username));
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
    public void createUser(String username, String password, String name, String email, Set<Role> roles) {
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setName(name);
        user.setEmail(email);
        user.setRoles(roles);
        User savedUser = userDao.save(user);
        System.out.println("User has been created, id: " + savedUser.getId());
    }

    @Transactional
    @Override
    public void updateUser(Long id, String username, String password, String name, String email, Set<Role> roles) {
        User user = userDao.findById(id);
        user.setName(name);
        user.setUsername(username);
        user.setEmail(email);
        if (!password.equals(passwordEncoder.encode(user.getPassword()))) {
            user.setPassword(passwordEncoder.encode(password));
        }
        user.setRoles(roles);
        userDao.update(user);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        userDao.delete(id);
    }

    @Transactional
    @Override
    public void deleteAllUsers() {
        userDao.deleteAllUsers();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.findByUsername(username);
    }
}
