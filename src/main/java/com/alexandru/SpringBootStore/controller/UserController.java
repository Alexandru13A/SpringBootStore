package com.alexandru.SpringBootStore.controller;


import com.alexandru.SpringBootStore.dto.AddressDTO;
import com.alexandru.SpringBootStore.model.Address;
import com.alexandru.SpringBootStore.model.User;
import com.alexandru.SpringBootStore.service.AddressService;
import com.alexandru.SpringBootStore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/shopping/user")
public class UserController {

    private final UserService userService;
    private final AddressService addressService;

    @Autowired
    public UserController(UserService userService, AddressService addressService) {
        this.userService = userService;

        this.addressService = addressService;
    }

    //ACCOUNT SETTINGS
    @GetMapping("/settings")
    public String accountSettings(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());
        model.addAttribute("username", user.getFullName(user.getFirstName(), user.getLastName()));
        model.addAttribute("email", user.getEmail());
        return "account/account_settings";
    }

    //Change first name
    @PostMapping("/updateFirstName")
    public String updateFirstName(@RequestParam("newFirstName") String newFirstName, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());

        if (newFirstName.isEmpty()) {
            model.addAttribute("username", user.getFullName(user.getFirstName(), user.getLastName()));
            model.addAttribute("email", user.getEmail());
            return "account/account_settings";
        }

        // Actualizează numele de familie al utilizatorului
        user.setFirstName(newFirstName);
        userService.createUser(user);

        model.addAttribute("username", user.getFullName(user.getFirstName(), user.getLastName()));
        model.addAttribute("email", user.getEmail());
        return "account/account_settings";
    }

    //Change last name
    @PostMapping("/updateLastName")
    public String updateLastName(@RequestParam("newLastName") String newLastName, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());

        if (newLastName.isEmpty()) {
            model.addAttribute("username", user.getFullName(user.getFirstName(), user.getLastName()));
            model.addAttribute("email", user.getEmail());
            return "account/account_settings";
        }

        // Actualizează numele de familie al utilizatorului
        user.setLastName(newLastName);
        userService.createUser(user);

        model.addAttribute("username", user.getFullName(user.getFirstName(), user.getLastName()));
        model.addAttribute("email", user.getEmail());

        return "account/account_settings";
    }

    //Change email
    //Change password

    //Address add/change
    @GetMapping("/address")
    public String addAddressToUser(Model model) {
        AddressDTO addressDTO = new AddressDTO();
        model.addAttribute("address", addressDTO);
        return "account/address_form";
    }

    @PostMapping("/saveAddress")
    public String saveAddress(@Valid @ModelAttribute AddressDTO addressDTO, BindingResult bindingResult, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());
        model.addAttribute("username", user.getFullName(user.getFirstName(), user.getLastName()));

        if (bindingResult.hasErrors()) {
            return "account/address_form";
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

        return "account/display_address";
    }

    //ORDERS

    //LOGOUT


}
