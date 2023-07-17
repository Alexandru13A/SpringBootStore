package com.alexandru.SpringBootStore.controller;

import com.alexandru.SpringBootStore.dto.UserDTO;
import com.alexandru.SpringBootStore.model.User;
import com.alexandru.SpringBootStore.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class AccountController {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserService userService;


    public AccountController(BCryptPasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }


    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        UserDTO userDTO = new UserDTO();
        model.addAttribute("user", userDTO);
        return "authentication/register_form";
    }


    @PostMapping("/save")
    public String saveUser(@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "authentication/register_form";
        }
        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "userDTO.confirmPassword", "Password and Confirm Password must match");
            model.addAttribute("users", userDTO);
            return "authentication/register_form";
        }

        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userService.createUser(user);

        model.addAttribute("user", user);
        return "authentication/display_form";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "authentication/login_form";
    }


    @GetMapping("/logout")
    public String logout() {
        return "users/home/dashboard";
    }


}
