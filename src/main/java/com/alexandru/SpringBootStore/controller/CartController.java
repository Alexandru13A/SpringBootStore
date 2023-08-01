package com.alexandru.SpringBootStore.controller;

import com.alexandru.SpringBootStore.model.Cart;
import com.alexandru.SpringBootStore.model.CartItem;
import com.alexandru.SpringBootStore.model.Product;
import com.alexandru.SpringBootStore.service.CartItemService;
import com.alexandru.SpringBootStore.service.CartService;
import com.alexandru.SpringBootStore.service.ProductService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;


@Controller
@RequestMapping("/shopping")
public class CartController {

    private final CartItemService cartItemService;
    private final CartService cartService;
    private final ProductService productService;

    public CartController(CartItemService cartItemService, CartService cartService, ProductService productService) {
        this.cartItemService = cartItemService;
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
    public String adminAddToCart(@PathVariable("productId") Long productId, @RequestParam("size") String size, @RequestParam("quantity") int quantity) {
        Product product = productService.getProductById(productId);
        Cart cart = cartService.getCartFromSession();



        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setSize(size);
        cartItem.setQuantity(quantity);

        cart.addCartItem(cartItem);

        cartService.updateCartInSession(cart);
        BigDecimal totalPrice = cartService.calculateTotalPrice(cart);
        cartService.updateTotalPriceInSession(totalPrice);
        return "redirect:/shopping/view/products";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/admin/removeFromCart/{productIndex}")
    public String adminRemoveFromCart(@PathVariable("productIndex") int productIndex) {
        Cart cart = cartService.getCartFromSession();
        cart.removeCartItem(productIndex);
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
    public String addToCart(@PathVariable("productId") Long productId, @RequestParam("size") String size, @RequestParam("quantity") int quantity) {
        Product product = productService.getProductById(productId);
        Cart cart = cartService.getCartFromSession();

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setSize(size);
        cartItem.setQuantity(quantity);

        cart.addCartItem(cartItem);

        cartService.updateCartInSession(cart);
        BigDecimal totalPrice = cartService.calculateTotalPrice(cart);
        cartService.updateTotalPriceInSession(totalPrice);
        return "redirect:/shopping/view/products";
    }


    @PostMapping("/user/removeFromCart/{productIndex}")
    public String removeFromCart(@PathVariable("productIndex") int productIndex) {
        Cart cart = cartService.getCartFromSession();
        cart.removeCartItem(productIndex);
        cartService.updateCartInSession(cart);
        BigDecimal totalPrice = cartService.calculateTotalPrice(cart);
        cartService.updateTotalPriceInSession(totalPrice);
        return "redirect:/shopping/users/cart";
    }


}
