package com.alexandru.SpringBootStore.controller;

import com.alexandru.SpringBootStore.dto.UserDTO;
import com.alexandru.SpringBootStore.model.User;
import com.alexandru.SpringBootStore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Controller
public class AccountController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    private UserService userService;


    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String showIndex() {
        return "index";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        UserDTO userDTO = new UserDTO();
        model.addAttribute("user", userDTO);
        return "user/register_form";
    }


    @PostMapping("/save")
    public String saveUser(@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "user/register_form";
        }
        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "userDTO.confirmPassword", "Password and Confirm Password must match");
            model.addAttribute("user", userDTO);
            return "user/register_form";
        }

        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userService.createUser(user);

        model.addAttribute("user", user);
        return "user/display_form";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "user/login_form";
    }


    @GetMapping("/logout")
    public String logout() {
        return "home/dashboard";
    }


}
