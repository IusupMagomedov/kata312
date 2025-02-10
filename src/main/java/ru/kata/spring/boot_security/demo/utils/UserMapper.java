package ru.kata.spring.boot_security.demo.utils;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.Set;

@Component
@AllArgsConstructor
public class UserMapper {
    private final RoleMapper roleMapper;

    public User toUser(UserDto userDto) {
        Set<Role> roles = roleMapper.getRoles(userDto.getRoles());

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setName(userDto.getName());
        user.setLastName(userDto.getLastName());
        user.setAge(Integer.parseInt(userDto.getAge()));
        user.setEmail(userDto.getEmail());
        user.setRoles(roles);

        return user;
    }

    public User toUserWithId(UserDto userDto) {
        User user = toUser(userDto);
        user.setId(Long.parseLong(userDto.getId()));
        return user;
    }
}
