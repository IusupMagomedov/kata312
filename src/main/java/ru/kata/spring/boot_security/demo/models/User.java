package ru.kata.spring.boot_security.demo.models;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    private String name;
    private String lastName;
    private int age;
    private String email;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    public String getStringRoles() {
        StringBuilder sBRoles = new StringBuilder();
        sBRoles = new StringBuilder(roles
                .stream()
                .map(role -> role.getName().substring(5))
                .collect(Collectors.joining(" ")));
        return sBRoles.toString();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }
}