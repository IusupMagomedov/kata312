package ru.kata.spring.boot_security.demo.controllers;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDTO {
    private String id;
    private String username;
    private String password;
    private String name;
    private String email;
    private String[] roles;
}
