package com.alexandru.SpringBootStore.controller;


import com.alexandru.SpringBootStore.dto.UserDTO;
import com.alexandru.SpringBootStore.model.User;
import com.alexandru.SpringBootStore.service.UserService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
@RequestMapping("/shopping")
public class HomeController {

    private UserService userService;


    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String redirectByRole(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userService.getUserByEmail(email);
        String fullName = user.getFullName(user.getFirstName(), user.getLastName());
        model.addAttribute("fullName", fullName);

        if (user != null) {
                String role = user.getRole();

                if (role.equals("ADMIN")) {
                    return "redirect:/shopping/admin/dashboard";
                } else if (role.equals("USER")) {
                    return "redirect:/shopping/user/dashboard";
                }
            }

        return "registerandlogin/login_form";
    }




    @GetMapping("/user/dashboard")
    public String showUserDashboard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            String email = authentication.getName();

            User user = userService.getUserByEmail(email);

            String fullName = user.getFullName(user.getFirstName(), user.getLastName());

            model.addAttribute("fullName", fullName);

        }

        return "users/home/user_dashboard";
    }

    @GetMapping("/admin/dashboard")
    public String showAdminDashboard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            String email = authentication.getName();

            User user = userService.getUserByEmail(email);

            String fullName = user.getFullName(user.getFirstName(), user.getLastName());

            model.addAttribute("fullName", fullName);

        }

        return "admin/home/admin_dashboard";
    }

}
