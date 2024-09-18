package com.ms.hoopi.repository;

import com.ms.hoopi.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("SELECT c FROM Category c WHERE c.boardCode.boardCode = :boardCode")
    List<Category> findAllByBoardCode(String boardCode);
}

