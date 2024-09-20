package com.ms.hoopi.repository;

import com.ms.hoopi.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    @Query(value = "SELECT * FROM product p ORDER BY p.created_at DESC LIMIT 10", nativeQuery = true)
    List<Product> findAllNew();

    @Query(value = "SELECT p.* FROM product p LEFT JOIN order_detail o ON p.product_code = o.product_code GROUP BY p.product_code ORDER BY SUM(o.quantity) DESC LIMIT 10", nativeQuery = true)
    List<Product> findAllPopular();
}
