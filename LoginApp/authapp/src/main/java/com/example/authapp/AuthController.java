package com.example.authapp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

//import com.example.authapp.User;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AuthController {

    // Simple in-memory list mimicking a database
    private static List<User> database = new ArrayList<>();

    // 1. Direct to Login Page (Default)
    @GetMapping("/")
    public String showLoginPage(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    // 2. Handle Login Submission
    @PostMapping("/login")
    public String loginUser(@ModelAttribute("user") User user, Model model) {
        for (User u : database) {
            if (u.getUsername().equals(user.getUsername()) && u.getPassword().equals(user.getPassword())) {
                model.addAttribute("username", user.getUsername());
                return "home"; // Redirects to home.html
            }
        }
        model.addAttribute("error", "Invalid username or password!");
        return "login";
    }

    // 3. Direct to Signup Page
    @GetMapping("/signup")
    public String showSignupPage(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    // 4. Handle Signup Submission
    @PostMapping("/signup")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        // Simple check to prevent duplicate usernames
        for (User u : database) {
            if (u.getUsername().equals(user.getUsername())) {
                model.addAttribute("error", "Username already exists!");
                return "signup";
            }
        }
        database.add(user);
        model.addAttribute("success", "Registration successful! Please login.");
        return "login";
    }
}
