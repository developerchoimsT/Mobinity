package com.ms.hoopi.repository;

import com.ms.hoopi.model.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, String> {
    @Query("SELECT a FROM Article a WHERE a.productCode.productCode = :productCode")
    String findByProductCode(String productCode);
}
