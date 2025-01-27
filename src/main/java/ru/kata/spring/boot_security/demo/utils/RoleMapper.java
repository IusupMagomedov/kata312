package ru.kata.spring.boot_security.demo.utils;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.service.RoleService;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class RoleMapper {
    private RoleService roleService;

    public Set<Role> getRoles(String[] roleIds) {
        return Arrays.stream(roleIds)
                .mapToLong(Long::parseLong)
                .mapToObj(roleService::getRole)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .peek(System.out::println)
                .collect(Collectors.toSet());
    }
}
