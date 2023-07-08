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

    private UserService userService;
    private AddressRepository addressRepository;

    public AddressService(UserService userService, AddressRepository addressRepository) {
        this.userService = userService;
        this.addressRepository = addressRepository;
    }


    public int updateFirstAddress(String address, long id) {
        return addressRepository.updateFirstAddress(address, id);
    }

    public int updateSecondAddress(Long id, String address) {
        return addressRepository.updateSecondAddress(id, address);
    }

    public int updateCity(long id, String city) {
        return addressRepository.updateCity(id, city);
    }

    public int updateCountry(long id, String country) {
        return addressRepository.updateCountry(id, country);
    }

    public void deleteAddress(long id) {
        addressRepository.deleteById(id);
    }

    public void saveAddress(Address address) {
        addressRepository.save(address);
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
