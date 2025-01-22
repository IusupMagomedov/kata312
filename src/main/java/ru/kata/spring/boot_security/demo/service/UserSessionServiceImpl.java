package ru.kata.spring.boot_security.demo.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.controllers.ObjectUtils;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserSessionServiceImpl implements UserSessionService {
    UserService userService;

    @Override
    public Optional<User> getAuthenticatedUser(UserDetails authenticatedUser) {
        if (authenticatedUser == null) {
            return Optional.empty();
        }
        User user = userService
                    .findByUsername(authenticatedUser.getUsername())
                    .orElseThrow(
                            () -> new UsernameNotFoundException("User not found")
                    );
        return Optional.of(user);
    }

    @Override
    public Map<String, Object> getUserProperties(UserDetails authenticatedUser) {
        User user = userService
                .findByUsername(authenticatedUser.getUsername())
                .orElseThrow(
                        () -> new UsernameNotFoundException("User not found")
                );
        return ObjectUtils.getObjectProperties(user);
    }

    @Override
    public boolean isAdmin(Authentication authentication) {
        Set<String> roles = AuthorityUtils
                .authorityListToSet(authentication.getAuthorities());
        return roles.contains("ROLE_ADMIN");
    }

    @Override
    public Set<String> getRoles(Authentication authentication) {
        return Set.of();
    }

    @Override
    public boolean isAuthenticated(Authentication authentication) {
        return authentication.isAuthenticated();
    }
}
