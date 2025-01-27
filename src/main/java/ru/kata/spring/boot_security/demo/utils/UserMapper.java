package ru.kata.spring.boot_security.demo.utils;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dto.UserDtoImpl;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.Set;

@Component
@AllArgsConstructor
public class UserMapper {
    private final RoleMapper roleMapper;

    public User toUser(UserDtoImpl userDtoImpl) {
        Set<Role> roles = roleMapper.getRoles(userDtoImpl.getRoles());

        User user = new User();
        user.setUsername(userDtoImpl.getUsername());
        user.setPassword(userDtoImpl.getPassword());
        user.setName(userDtoImpl.getName());
        user.setEmail(userDtoImpl.getEmail());
        user.setRoles(roles);

        return user;
    }

    public User toUserWithId(UserDtoImpl userDtoImpl) {
        User user = toUser(userDtoImpl);
        user.setId(Long.parseLong(userDtoImpl.getId()));
        return user;
    }
}
