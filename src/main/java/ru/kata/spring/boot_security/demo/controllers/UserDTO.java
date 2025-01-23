package ru.kata.spring.boot_security.demo.controllers;

import lombok.Getter;

@Getter
public class UserDTO {
    private String username;
    private String password;
    private String name;
    private String email;
    private String[] roles;
}
