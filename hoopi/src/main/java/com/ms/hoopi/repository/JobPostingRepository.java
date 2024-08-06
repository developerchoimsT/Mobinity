package com.ms.hoopi.repository;

import com.ms.hoopi.model.entity.Company;
import com.ms.hoopi.model.entity.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public interface JobPostingRepository extends JpaRepository<JobPosting, Integer> {
    @Override
    List<JobPosting> findAll();

    @Query("SELECT j FROM JobPosting j WHERE " +
            "j.jobPostingBody LIKE %:search% OR " +
            "j.jobPostingMoney LIKE %:search% OR " +
            "j.jobPostingSkill LIKE %:search% OR " +
            "j.jobPostingPosition LIKE %:search%")
    List<JobPosting> searchJobPostings(@Param("search") String search);
}
