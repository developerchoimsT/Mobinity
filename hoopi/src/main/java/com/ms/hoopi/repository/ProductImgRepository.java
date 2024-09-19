package com.ms.hoopi.repository;

import com.ms.hoopi.model.entity.ProductImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImgRepository extends JpaRepository<ProductImg, String> {
    @Query(value = "SELECT pi.product_img_code, pi.product_code, pi.file_name" +
            ", pi.img_path, pi.img_key, pi.created_at " +
            "FROM product_img pi " +
            "WHERE pi.product_code = :productCode " +
            "ORDER BY pi.created_at asc " +
            "LIMIT 1",
            nativeQuery = true)
    ProductImg findByProductCode(String productCode);
}
