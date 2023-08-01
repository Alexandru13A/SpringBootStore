package com.alexandru.SpringBootStore.service;

import com.alexandru.SpringBootStore.model.Cart;
import com.alexandru.SpringBootStore.model.CartItem;
import com.alexandru.SpringBootStore.model.Product;
import com.alexandru.SpringBootStore.repository.CartItemRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public List<String> shoesSize() {
        List<String> size = new ArrayList<>();
        size.add("36");
        size.add("36.5");
        size.add("37");
        size.add("37.5");
        size.add("38");
        size.add("38.5");
        size.add("39");
        size.add("39.5");
        size.add("40");
        size.add("40.5");
        size.add("41");
        size.add("41.5");
        size.add("42");
        size.add("42.5");
        size.add("43");
        size.add("43.5");
        size.add("44");
        size.add("44.5");
        size.add("45");

        return size;
    }

    public List<String> clothesSize() {
        List<String> size = new ArrayList<>();
        size.add("XXS");
        size.add("XS");
        size.add("S");
        size.add("M");
        size.add("L");
        size.add("XL");
        size.add("XXL");
        size.add("XXXL");
        return size;

    }

    public void saveCartItem(CartItem cartItem) {
        cartItemRepository.save(cartItem);
    }


    public List<CartItem> getCartItemsByCart(Cart cart) {
        return cart.getCartItems();
    }


}
