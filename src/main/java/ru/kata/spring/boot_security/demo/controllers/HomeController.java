package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String getHomePage(Model model) {
        return "home";
    }
    @GetMapping("/login")
    public String getLoginPage(Model model) {
        return "login";
    }
    @GetMapping("/logout")
    public String getLogoutPage(Model model) {
        return "logout";
    }
    @GetMapping("/user")
    public String getUserPage(Model model) {
        return "user";
    }
}