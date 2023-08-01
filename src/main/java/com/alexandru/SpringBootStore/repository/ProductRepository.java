package com.alexandru.SpringBootStore.repository;

import com.alexandru.SpringBootStore.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE  LOWER(p.name) LIKE %:searchTerm%")
    List<Product> findByName(@Param("searchTerm") String searchTerm);


    @Query("SELECT p FROM Product p " +
            "WHERE (:category IS NULL OR p.category = :category) " +
            "ORDER BY " +
            "CASE WHEN :sort = 'price' THEN p.price END ASC, " +
            "CASE WHEN :sort = 'name' THEN p.name END ASC, " +
            "CASE WHEN :sort != 'price' AND :sort != 'name' THEN p.id END ASC")
    List<Product> sortByCategoryAndColumn(@Param("category") String category, @Param("sort") String sort);
}
