package com.alexandru.SpringBootStore.controller;

import com.alexandru.SpringBootStore.dto.UserDTO;
import com.alexandru.SpringBootStore.model.User;
import com.alexandru.SpringBootStore.service.UserService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
        user.setPassword(userDTO.getPassword());
        userService.createUser(user);
        model.addAttribute("user", user);
        return "display_form";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login_form";
    }
    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        // Obține utilizatorul autentificat din contextul de securitate
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Verifică dacă utilizatorul este autentificat și nu este anonim
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            // Obține detaliile utilizatorului autentificat
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Adaugă detaliile utilizatorului în model
            model.addAttribute("username", userDetails.getUsername());
        }

        return "dashboard";
    }




}
