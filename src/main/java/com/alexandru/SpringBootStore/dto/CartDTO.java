package com.alexandru.SpringBootStore.dto;

import java.util.List;

public class CartDTO {

    private long cartId;

    private List<ProductDTO> products;

    public long getCartId() {
        return cartId;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }
}
