package com.alexandru.SpringBootStore.controller;


import com.alexandru.SpringBootStore.dto.AddressDTO;
import com.alexandru.SpringBootStore.dto.OrderDTO;
import com.alexandru.SpringBootStore.model.*;
import com.alexandru.SpringBootStore.service.AddressService;
import com.alexandru.SpringBootStore.service.CartService;
import com.alexandru.SpringBootStore.service.OrderService;
import com.alexandru.SpringBootStore.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/shopping")
public class OrderController {

    private AddressService addressService;
    private OrderService orderService;
    private UserService userService;
    private CartService cartService;

    public OrderController(AddressService addressService, OrderService orderService, UserService userService, CartService cartService) {
        this.addressService = addressService;
        this.orderService = orderService;
        this.userService = userService;
        this.cartService = cartService;
    }


    @GetMapping("/orders")
    public String getOrders(Model model, @ModelAttribute("order") Order order) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getUserByEmailWithOrders(userDetails.getUsername());
        Address address = user.getAddress();

        // Obține toate comenzile utilizatorului
        List<Order> orders = user.getOrders();
        BigDecimal totalPrice = null;
        // Calculează prețul total pentru fiecare comandă
        for (Order o : orders) {
            totalPrice = cartService.calculateTotalPrice(o.getCart());
        }

        String fullAddress = "Address 1: " + address.getAddress1() + ", City: " + address.getCity() + ", Country: " + address.getCountry();
        String fullName = user.getFullName(user.getFirstName(), user.getLastName());
        model.addAttribute("orders", orders);
        model.addAttribute("fullAddress", fullAddress);
        model.addAttribute("fullName", fullName);
        model.addAttribute("totalPrice", totalPrice);
        if(user.getRole().equals("ADMIN")) {
            return "admin/home/orders";
        }else {
            return "users/functions/orders";
        }
    }

    @GetMapping("/order/create")
    public String createOrder(Model model) {
        OrderDTO orderDTO = new OrderDTO();
        model.addAttribute("order", orderDTO);
        return "common/create_order";
    }


    @PostMapping("/order/save")
    public String placeOrder(HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());
        Cart cart = cartService.getCartFromSession();


        if (cart != null) {
            cart.setUser(user);
            cartService.saveCart(cart);
        }

        Order order = new Order();
        order.setUser(user);
        order.setCart(cart);

        orderService.saveOrder(order);

        session.removeAttribute("cart");
        return "common/order_success";
    }

    @GetMapping("/preview/order")
    public String previewOrder(Model model, HttpSession session, BigDecimal totalPrice) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());
        Cart cart = (Cart) session.getAttribute("cart");
        Address address = user.getAddress();

        if (address == null) {
            return "redirect:/shopping/create/address";
        }

        String fullAddress = "Address 1: " + address.getAddress1() + ", City: " + address.getCity() + ", Country: " + address.getCountry();
        String fullName = user.getFullName(user.getFirstName(), user.getLastName());
        totalPrice = cartService.calculateTotalPrice(cart);

        model.addAttribute("user", user);
        model.addAttribute("cart", cart);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("fullName", fullName);
        model.addAttribute("fullAddress", fullAddress);


        return "common/preview_order";

    }

    @GetMapping("/create/address")
    public String addAddressToUser(Model model) {
        AddressDTO addressDTO = new AddressDTO();
        model.addAttribute("address", addressDTO);
        model.addAttribute("username", userService.getLoggedInUsername());
        return "common/order_address_if_null";
    }

    @PostMapping("/order/address/save")
    public String saveAddress(@Valid @ModelAttribute AddressDTO addressDTO, BindingResult bindingResult, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());

        if (bindingResult.hasErrors()) {
            return "common/order_address_if_null";
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

        return "redirect:/shopping/preview/order";
    }

    @PostMapping("/order/delete/{id}")
    public String deleteOrder(@PathVariable("id") Long orderId) {
        orderService.deleteOrder(orderId);
        return "redirect:/shopping/orders";
    }
}
