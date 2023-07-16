package com.alexandru.SpringBootStore.repository;

import com.alexandru.SpringBootStore.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {



    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.carts WHERE p.id = :id")
    Product getProductById(@Param("id") Long id);
}
