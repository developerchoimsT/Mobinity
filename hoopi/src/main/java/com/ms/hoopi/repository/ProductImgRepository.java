package com.ms.hoopi.repository;

import com.ms.hoopi.model.entity.Product;
import com.ms.hoopi.model.entity.ProductImg;
import com.ms.hoopi.product.model.ProductImgResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductImgRepository extends JpaRepository<ProductImg, String> {
    @Query(value = "SELECT pi.product_img_code, pi.product_code, pi.file_name" +
            ", pi.img_path, pi.img_key, pi.created_at " +
            "FROM product_img pi " +
            "WHERE pi.product_code = :productCode " +
            "ORDER BY pi.created_at asc " +
            "LIMIT 1",
            nativeQuery = true)
    ProductImgResponseDto findByProductCode(String productCode);
}
