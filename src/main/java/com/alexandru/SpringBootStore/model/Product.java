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
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productId;

    @Column(name = "product_name")
    private String name;

    @Column(name = "product_short_description")
    private String shortDescription;

    @Column(name = "product_long_description")
    private String longDescription;

    @Column(name = "product_price")
    private BigDecimal price;

    @Column(name = "product_category")
    private String category;


    @Column(name = "product_image")
    private byte[] productImage;

    @ManyToMany(mappedBy = "products")
    private List<Cart> carts = new ArrayList<>();

}
