package ru.kata.spring.boot_security.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDtoImpl implements UserDto {
    private String id;
    private String username;
    private String password;
    private String name;
    private String email;
    private String[] roles;
}
