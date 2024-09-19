package com.ms.hoopi.repository;

import com.ms.hoopi.model.entity.Product;
import com.ms.hoopi.product.model.ProductResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    @Query(value = "SELECT * FROM product p ORDER BY p.created_at DESC LIMIT 10", nativeQuery = true)
    List<Product> findAllNew();
}
