package ru.kata.spring.boot_security.demo.models;

import jakarta.persistence.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Entity
@Table
public class Role {
    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
