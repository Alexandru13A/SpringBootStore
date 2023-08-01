package com.alexandru.SpringBootStore.repository;

import com.alexandru.SpringBootStore.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {


    @Query("SELECT ci, p FROM CartItem ci LEFT JOIN FETCH ci.product p WHERE ci.id = :id AND p.category = :category")
    CartItem getProductByIdAndCategory(@Param("id") Long id, @Param("category") String category);

}
