package com.alexandru.SpringBootStore.controller;

import com.alexandru.SpringBootStore.model.User;
import com.alexandru.SpringBootStore.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller

public class UserController {

    private UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/users")
    public String allUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }


    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register_form";
    }


    @PostMapping("/save")
    public String saveUser(@ModelAttribute User user, Model model) {
        model.addAttribute("user", user);
        return "display_form";
    }


}
