package com.alexandru.SpringBootStore.service;

import com.alexandru.SpringBootStore.model.Cart;
import com.alexandru.SpringBootStore.model.Order;
import com.alexandru.SpringBootStore.model.User;
import com.alexandru.SpringBootStore.repository.OrderRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class OrderService {

    private CartService cartService;
    private UserService userService;
    private OrderRepository orderRepository;

    public OrderService(CartService cartService, UserService userService, OrderRepository orderRepository) {
        this.cartService = cartService;
        this.userService = userService;
        this.orderRepository = orderRepository;
    }


    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
