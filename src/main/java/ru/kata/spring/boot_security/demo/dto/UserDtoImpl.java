package ru.kata.spring.boot_security.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserDtoImpl implements UserDto {
    private String id;
    private String username;
    private String password;
    private String name;
    private String lastName;
    private String age;
    private String email;
    private String[] roles;
}
