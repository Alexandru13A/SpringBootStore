package com.alexandru.SpringBootStore.controller;

import com.alexandru.SpringBootStore.dto.AddressDTO;
import com.alexandru.SpringBootStore.dto.UserDTO;
import com.alexandru.SpringBootStore.model.Address;
import com.alexandru.SpringBootStore.model.User;
import com.alexandru.SpringBootStore.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/shopping")
public class AdminController {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    private final UserService userService;
    private final OrderService orderService;
    private final ProductService productService;
    private final AddressService addressService;


    public AdminController(BCryptPasswordEncoder passwordEncoder, UserService userService, OrderService orderService, ProductService productService, AddressService addressService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.orderService = orderService;
        this.productService = productService;
        this.addressService = addressService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/users")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/home/users";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/updateAddress/{id}")
    public String editAddress(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        user.setUserId(id);
        Address address = user.getAddress();
        user.setUserId(id);
        if (address == null) {
            model.addAttribute("userId", user.getUserId());
            model.addAttribute("username", user.getFullName(user.getFirstName(), user.getLastName()));
            return "admin/functions/create_address";
        }
        model.addAttribute("address", address);
        model.addAttribute("user", user);
        model.addAttribute("userId", user.getUserId());

        return "admin/functions/modify_user_address";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/admin/createAddress/{id}")
    public String saveAddress(@PathVariable Long id, @Valid @ModelAttribute AddressDTO addressDTO, BindingResult bindingResult, Model model) {
        User user = userService.getUserById(id);
        user.setUserId(id);
        if (bindingResult.hasErrors()) {
            return "admin/functions/create_address";
        }

        Address address = new Address();
        address.setAddress1(addressDTO.getAddress1());
        address.setAddress2(addressDTO.getAddress2());
        address.setCity(addressDTO.getCity());
        address.setCountry(addressDTO.getCountry());

        addressService.saveAddress(address);
        user.setAddress(address);
        userService.createUser(user);
        model.addAttribute("address", address);
        model.addAttribute("user", user);
        model.addAttribute("username", user.getFullName(user.getFirstName(), user.getLastName()));
        model.addAttribute("email", user.getEmail());
        model.addAttribute("role", user.getRole());
        model.addAttribute("id", user.getUserId());

        return "/admin/functions/modify_user";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/update/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        user.setUserId(id);
        model.addAttribute("user", user);
        model.addAttribute("username", user.getFullName(user.getFirstName(), user.getLastName()));
        model.addAttribute("email", user.getEmail());
        model.addAttribute("role", user.getRole());
        model.addAttribute("id", user.getUserId());

        Address address = user.getAddress();
        model.addAttribute("address", address);
        model.addAttribute("userId", user.getUserId());
        return "admin/functions/modify_user";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("admin/modifyAddress1/{id}")
    public String updateAddress1(@PathVariable Long id, @RequestParam("address1") String address1, Model model) {
        addressService.adminModifyAddress1(id, address1, model);
        return "admin/functions/modify_user_address";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("admin/modifyAddress2/{id}")
    public String updateAddress2(@PathVariable Long id, @RequestParam("address2") String address2, Model model) {
        addressService.adminModifyAddress2(id, address2, model);
        return "admin/functions/modify_user_address";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("admin/modifyCity/{id}")
    public String updateCity(@PathVariable Long id, @RequestParam("city") String city, Model model) {
        addressService.adminModifyCity(id, city, model);
        return "admin/functions/modify_user_address";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("admin/modifyCountry/{id}")
    public String updateCountry(@PathVariable Long id, @RequestParam("country") String country, Model model) {
        addressService.adminModifyCountry(id, country, model);
        return "admin/functions/modify_user_address";
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/admin/updateRole/{id}")
    public String updateRole(@PathVariable Long id, @RequestParam("newRole") String newRole, Model model) {
        User user = userService.getUserById(id);

        if (newRole.isEmpty()) {
            model.addAttribute("username", user.getFullName(user.getFirstName(), user.getLastName()));
            model.addAttribute("email", user.getEmail());
            return "admin/functions/modify_user";
        }

        // Actualizează numele de familie al utilizatorului
        user.setUserId(id);
        user.setRole(newRole);
        userService.createUser(user);

        model.addAttribute("user", user);
        model.addAttribute("username", user.getFullName(user.getFirstName(), user.getLastName()));
        model.addAttribute("email", user.getEmail());
        model.addAttribute("role", user.getRole());
        model.addAttribute("id", user.getUserId());
        Address address = user.getAddress();
        model.addAttribute("address", address);


        return "admin/functions/modify_user";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/admin/updateFirstName/{id}")
    public String updateFirstName(@PathVariable Long id, @RequestParam("newFirstName") String newFirstName, Model model) {
        User user = userService.getUserById(id);

        if (newFirstName.isEmpty()) {
            model.addAttribute("username", user.getFullName(user.getFirstName(), user.getLastName()));
            model.addAttribute("email", user.getEmail());
            return "admin/functions/modify_user";
        }

        // Actualizează numele de familie al utilizatorului
        user.setUserId(id);
        user.setFirstName(newFirstName);
        userService.createUser(user);

        model.addAttribute("user", user);
        model.addAttribute("username", user.getFullName(user.getFirstName(), user.getLastName()));
        model.addAttribute("email", user.getEmail());
        model.addAttribute("role", user.getRole());
        model.addAttribute("id", user.getUserId());
        Address address = user.getAddress();
        model.addAttribute("address", address);


        return "admin/functions/modify_user";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/admin/updateLastName/{id}")
    public String updateLastName(@PathVariable Long id, @RequestParam("newLastName") String newLastName, Model model) {
        User user = userService.getUserById(id);

        if (newLastName.isEmpty()) {
            model.addAttribute("username", user.getFullName(user.getFirstName(), user.getLastName()));
            model.addAttribute("email", user.getEmail());
            return "admin/functions/modify_user";
        }

        // Actualizează numele de familie al utilizatorului
        user.setUserId(id);
        user.setLastName(newLastName);
        userService.createUser(user);

        model.addAttribute("user", user);
        model.addAttribute("username", user.getFullName(user.getFirstName(), user.getLastName()));
        model.addAttribute("email", user.getEmail());
        model.addAttribute("role", user.getRole());
        model.addAttribute("id", user.getUserId());
        Address address = user.getAddress();
        model.addAttribute("address", address);


        return "admin/functions/modify_user";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/admin/updateEmail/{id}")
    public String updateEmail(@PathVariable Long id, @RequestParam("email") String email, Model model) {
        User user = userService.getUserById(id);

        if (email.isEmpty()) {
            model.addAttribute("username", user.getFullName(user.getFirstName(), user.getLastName()));
            model.addAttribute("email", user.getEmail());
            return "admin/functions/modify_user";
        }

        // Actualizează numele de familie al utilizatorului
        user.setUserId(id);
        user.setEmail(email);
        userService.createUser(user);

        model.addAttribute("user", user);
        model.addAttribute("username", user.getFullName(user.getFirstName(), user.getLastName()));
        model.addAttribute("email", user.getEmail());
        model.addAttribute("role", user.getRole());
        model.addAttribute("id", user.getUserId());
        Address address = user.getAddress();
        model.addAttribute("address", address);


        return "admin/functions/modify_user";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("admin/modifyAddress")
    public String addressSettings(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());


        Address address = user.getAddress();
        model.addAttribute("address", address);

        return "users/account/modify_address";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/shopping/admin/users";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/createUser")
    public String createUser(Model model) {
        UserDTO userDTO = new UserDTO();
        model.addAttribute("user", userDTO);
        return "admin/functions/create_user";
    }


    @PostMapping("/admin/save")
    public String saveUser(@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "admin/functions/create_user";
        }
        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "userDTO.confirmPassword", "Password and Confirm Password must match");
            model.addAttribute("user", userDTO);
            return "admin/functions/create_user";
        }
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userService.createUser(user);
        model.addAttribute("user", user);

        return "redirect:/shopping/admin/users";
    }


}
