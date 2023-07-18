package com.alexandru.SpringBootStore.service;

import com.alexandru.SpringBootStore.model.Cart;
import com.alexandru.SpringBootStore.model.Product;
import com.alexandru.SpringBootStore.repository.CartRepository;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

@Service
public class CartService {


    private final HttpSession session;
    private final CartRepository cartRepository;

    public CartService(HttpSession session, CartRepository cartRepository) {
        this.session = session;
        this.cartRepository = cartRepository;
    }


    public void saveCart(Cart cart) {
        cartRepository.save(cart);
    }

    public Cart getCartFromSession() {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        } else {
            Hibernate.initialize(cart.getProducts());
            if (cart.getProducts() != null && !cart.getProducts().isEmpty()) {
                Hibernate.initialize(cart.getProducts().get(0).getCarts());
            }
        }
        return cart;
    }

    public void updateCartInSession(Cart cart) {
        session.setAttribute("cart", cart);

    }

    public void updateTotalPriceInSession(BigDecimal totalPrice) {
        session.setAttribute("totalPrice", totalPrice);
    }

    public BigDecimal calculateTotalPrice(Cart cart) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (Product product : cart.getProducts()) {
            totalPrice = totalPrice.add(product.getPrice());
        }

        return totalPrice;
    }


}
