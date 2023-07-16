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

        Address address = user.getAddress();
        model.addAttribute("address", address);

        return "user/account/account_settings";
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
            return "user/account/account_settings";
        }


        // Actualizează numele de familie al utilizatorului
        user.setFirstName(newFirstName);
        userService.createUser(user);

        model.addAttribute("username", user.getFullName(user.getFirstName(), user.getLastName()));
        model.addAttribute("email", user.getEmail());
        Address address = user.getAddress();
        model.addAttribute("address", address);


        return "user/account/account_settings";
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
            return "user/account/account_settings";
        }

        // Actualizează numele de familie al utilizatorului
        user.setLastName(newLastName);
        userService.createUser(user);

        model.addAttribute("username", user.getFullName(user.getFirstName(), user.getLastName()));
        model.addAttribute("email", user.getEmail());
        Address address = user.getAddress();
        model.addAttribute("address", address);


        return "user/account/account_settings";
    }

    //Change email
    //Change password

    //Address add/change
    @GetMapping("/address")
    public String addAddressToUser(Model model) {
        AddressDTO addressDTO = new AddressDTO();
        model.addAttribute("address", addressDTO);
        model.addAttribute("username", userService.getLoggedInUsername());
        return "user/account/address_form";
    }

    @PostMapping("/saveAddress")
    public String saveAddress(@Valid @ModelAttribute AddressDTO addressDTO, BindingResult bindingResult, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());

        if (bindingResult.hasErrors()) {
            return "user/account/address_form";
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

        return "user/account/display_address";
    }


    @PostMapping("/modifyAddress1")
    public String updateAddress1(@RequestParam("address1") String address1, Model model) {
        addressService.modifyAddress1(address1, model);
        return "user/account/modify_address";
    }

    @PostMapping("/modifyAddress2")
    public String updateAddress2(@RequestParam("address2") String address2, Model model) {
        addressService.modifyAddress2(address2, model);
        return "user/account/modify_address";
    }

    @PostMapping("/modifyCity")
    public String updateCity(@RequestParam("city") String city, Model model) {
        addressService.modifyCity(city, model);
        return "user/account/modify_address";
    }

    @PostMapping("/modifyCountry")
    public String updateCountry(@RequestParam("country") String country, Model model) {
        addressService.modifyCountry(country, model);
        return "user/account/modify_address";
    }

    @GetMapping("/modifyAddress")
    public String addressSettings(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());


        Address address = user.getAddress();
        model.addAttribute("address", address);

        return "user/account/modify_address";
    }


    //ORDERS




}
