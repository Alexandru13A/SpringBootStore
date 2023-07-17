package com.alexandru.SpringBootStore.service;


import com.alexandru.SpringBootStore.model.Address;
import com.alexandru.SpringBootStore.model.User;
import com.alexandru.SpringBootStore.repository.AddressRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class AddressService {

    private final UserService userService;
    private final AddressRepository addressRepository;

    public AddressService(UserService userService, AddressRepository addressRepository) {
        this.userService = userService;
        this.addressRepository = addressRepository;
    }


    public void saveAddress(Address address) {
        addressRepository.save(address);
    }

    public void adminModifyAddress1(Long id, String modify, Model model) {
        User user = userService.getUserById(id);
        if (user != null) {
            user.setUserId(id);
            Address address = user.getAddress();
            if (address != null) {
                address.setAddress1(modify);
                saveAddress(address);
                model.addAttribute("address", address);
                model.addAttribute("userId",user.getUserId());
            }
        }
    }
    public void adminModifyAddress2(Long id, String modify, Model model) {
        User user = userService.getUserById(id);
        if (user != null) {
            user.setUserId(id);
            Address address = user.getAddress();
            if (address != null) {
                address.setAddress2(modify);
                saveAddress(address);
                model.addAttribute("address", address);
                model.addAttribute("userId",user.getUserId());
            }
        }
    }
    public void adminModifyCity(Long id, String modify, Model model) {
        User user = userService.getUserById(id);
        if (user != null) {
            user.setUserId(id);
            Address address = user.getAddress();
            if (address != null) {
                address.setCity(modify);
                saveAddress(address);
                model.addAttribute("address", address);
                model.addAttribute("userId",user.getUserId());
            }
        }
    }
    public void adminModifyCountry(Long id, String modify, Model model) {
        User user = userService.getUserById(id);
        if (user != null) {
            user.setUserId(id);
            Address address = user.getAddress();
            if (address != null) {
                address.setCountry(modify);
                saveAddress(address);
                model.addAttribute("address", address);
                model.addAttribute("userId",user.getUserId());
            }
        }
    }

    public void modifyAddress1(String modify, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());

        Address address = user.getAddress();

        address.setAddress1(modify);
        saveAddress(address);
        model.addAttribute("address", address);

    }

    public void modifyAddress2(String modify, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());

        Address address = user.getAddress();

        address.setAddress2(modify);
        saveAddress(address);
        model.addAttribute("address", address);

    }

    public void modifyCity(String modify, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());

        Address address = user.getAddress();

        address.setCity(modify);
        saveAddress(address);
        model.addAttribute("address", address);

    }

    public void modifyCountry(String modify, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());

        Address address = user.getAddress();

        address.setCountry(modify);
        saveAddress(address);
        model.addAttribute("address", address);

    }


}
