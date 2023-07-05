package com.alexandru.SpringBootStore.controller;

import com.alexandru.SpringBootStore.model.User;
import com.alexandru.SpringBootStore.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/shopping/user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //ACCOUNT SETTINGS
    @GetMapping("/settings")
    public String accountSettings(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername());

        model.addAttribute("username", user.getFullName(user.getFirstName(), user.getLastName()));
        model.addAttribute("email", user.getEmail());

        return "account/account_settings";
    }

    //Change name
    @PostMapping("/updateFirstName")
    public String updateFirstName(@RequestParam("firstName") String newFirstName, Principal principal) {
        String email = principal.getName();
        userRepository.updateUserFirstName(email, newFirstName);
        return "redirect: account/account_settings";
    }
    //Change email
    //Change password

    //Address add/change

    //ORDERS

    //LOGOUT


}
