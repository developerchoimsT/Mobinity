package com.ms.hoopi.repository;

import com.ms.hoopi.model.entity.ArticleImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleImgRepository extends JpaRepository<ArticleImg, String> {
}
