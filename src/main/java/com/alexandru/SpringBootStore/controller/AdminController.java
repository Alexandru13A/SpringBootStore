package com.alexandru.SpringBootStore.controller;

import com.alexandru.SpringBootStore.service.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shopping")
public class AdminController {

    private UserService userService;
    private OrderService orderService;

    private ProductService productService;
    private AddressService addressService;


    public AdminController(UserService userService, OrderService orderService,  ProductService productService, AddressService addressService) {
        this.userService = userService;
        this.orderService = orderService;
        this.productService = productService;
        this.addressService = addressService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/users")
    public String getAllUsers() {

        userService.getAllUsers();
        return "admin/home/users";
    }


}
