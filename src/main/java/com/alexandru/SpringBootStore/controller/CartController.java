package com.alexandru.SpringBootStore.controller;

import com.alexandru.SpringBootStore.model.Cart;
import com.alexandru.SpringBootStore.model.Product;
import com.alexandru.SpringBootStore.service.CartService;
import com.alexandru.SpringBootStore.service.ProductService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


@Controller
@RequestMapping("/shopping")
public class CartController {


    private final CartService cartService;
    private final ProductService productService;

    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/cart")
    public String adminViewCart(Model model) {
        Cart cart = cartService.getCartFromSession();
        BigDecimal totalPrice = cartService.calculateTotalPrice(cart);
        model.addAttribute("cart", cart);
        model.addAttribute("totalPrice", totalPrice);
        return "admin/home/cart";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/admin/addToCart/{productId}")
    public String adminAddToCart(@PathVariable("productId") Long productId) {
        Product product = productService.productById(productId);
        Cart cart = cartService.getCartFromSession();
        cart.addProduct(product);
        cartService.updateCartInSession(cart);
        BigDecimal totalPrice = cartService.calculateTotalPrice(cart);
        cartService.updateTotalPriceInSession(totalPrice);
        return "redirect:/shopping/admin/view/products";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/admin/removeFromCart/{productIndex}")
    public String adminRemoveFromCart(@PathVariable("productIndex") int productIndex) {
        Cart cart = cartService.getCartFromSession();
        cart.removeProduct(productIndex);
        cartService.updateCartInSession(cart);
        BigDecimal totalPrice = cartService.calculateTotalPrice(cart);
        cartService.updateTotalPriceInSession(totalPrice);
        return "redirect:/shopping/admin/cart";
    }
    @GetMapping("/user/cart")
    public String viewCart(Model model) {
        Cart cart = cartService.getCartFromSession();
        BigDecimal totalPrice = cartService.calculateTotalPrice(cart);
        model.addAttribute("cart", cart);
        model.addAttribute("totalPrice", totalPrice);
        return "users/functions/cart";
    }

    @PostMapping("/user/addToCart/{productId}")
    public String addToCart(@PathVariable("productId") Long productId) {
        Product product = productService.productById(productId);
        Cart cart = cartService.getCartFromSession();
        cart.addProduct(product);
        cartService.updateCartInSession(cart);
        BigDecimal totalPrice = cartService.calculateTotalPrice(cart);
        cartService.updateTotalPriceInSession(totalPrice);
        return "redirect:/shopping/user/view/products";
    }


    @PostMapping("/user/removeFromCart/{productIndex}")
    public String removeFromCart(@PathVariable("productIndex") int productIndex) {
        Cart cart = cartService.getCartFromSession();
        cart.removeProduct(productIndex);
        cartService.updateCartInSession(cart);
        BigDecimal totalPrice = cartService.calculateTotalPrice(cart);
        cartService.updateTotalPriceInSession(totalPrice);
        return "redirect:/shopping/users/cart";
    }


}
