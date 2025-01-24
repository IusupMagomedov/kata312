package ru.kata.spring.boot_security.demo.controllers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.kata.spring.boot_security.demo.models.User;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

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
