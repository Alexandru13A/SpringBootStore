package com.alexandru.SpringBootStore.service;

import com.alexandru.SpringBootStore.model.Order;
import com.alexandru.SpringBootStore.repository.OrderRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService( OrderRepository orderRepository) {
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
