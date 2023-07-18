package com.alexandru.SpringBootStore.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private long cartId;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "cart")
    private Order order;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "cart_product",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products = new ArrayList<>();

    @Column(name = "cart_total_price")
    private BigDecimal cartTotalPrice;


    public void addProduct(Product product) {
        products.add(product);
        product.getCarts().add(this);
    }

    public void removeProduct(int index) {
        if (index >= 0 && index < products.size()) {
            Product product = products.get(index);
            products.remove(index);
            product.getCarts().remove(this);
        }
    }

}
