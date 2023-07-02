package com.alexandru.SpringBootStore.controller;

import com.alexandru.SpringBootStore.dto.UserDTO;
import com.alexandru.SpringBootStore.model.User;
import com.alexandru.SpringBootStore.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private BCryptPasswordEncoder passwordEncoder;
    private UserService userService;


    public UserController(BCryptPasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }


    @GetMapping("/users")
    public String allUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }


    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        UserDTO userDTO = new UserDTO();
        model.addAttribute("user", userDTO);
        return "register_form";
    }


    @PostMapping("/save")
    public String saveUser(@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "register_form";
        }
        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "userDTO.confirmPassword", "Password and Confirm Password must match");
            model.addAttribute("user", userDTO);
            return "register_form";
        }

        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userService.createUser(user);

        try {
            userService.createUser(user);
            logger.info("Utilizatorul a fost salvat în baza de date: {}", user);
        } catch (Exception e) {
            logger.error("Eroare la salvarea utilizatorului în baza de date: {}", e.getMessage());
            e.printStackTrace();
        }

        model.addAttribute("user", user);
        return "display_form";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login_form";
    }


    @GetMapping("/logout")
    public String logout() {
        return "dashboard";
    }


}
