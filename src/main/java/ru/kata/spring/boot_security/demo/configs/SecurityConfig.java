package ru.kata.spring.boot_security.demo.configs;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration
        .EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers
        .AbstractHttpConfigurer;
import ru.kata.spring.boot_security.demo.controllers.LoginController;
import ru.kata.spring.boot_security.demo.service.UserService;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
    private final SuccessUserHandler successUserHandler;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, LoginController loginController)
            throws Exception {
        http
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/user")
                                .hasRole("USER")
                                .requestMatchers("/user", "/admin/**")
                                .hasRole("ADMIN")
                                .anyRequest().authenticated())
                .formLogin(
                        form -> form
                                .loginPage("/login")
                                .successHandler(successUserHandler)
                                .permitAll())
                .logout(
                        logout -> logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/"))
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            @Autowired UserService userService) {
        DaoAuthenticationProvider authenticationProvider =
                new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}