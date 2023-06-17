package com.alexandru.SpringBootStore.dto;

public class OrderDTO {

    private long orderId;
    private CartDTO cart;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public CartDTO getCart() {
        return cart;
    }

    public void setCart(CartDTO cart) {
        this.cart = cart;
    }
}
