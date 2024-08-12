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

    JobPosting findJobPostingByJobPostingCd(@Param("jobPostingCd") int jobPostingCd);

    @Query("SELECT j FROM JobPosting j, Company c WHERE " +
            "j.companyCd = c.companyCd")
    List<JobPosting> findJobPosting();

    @Query("SELECT j FROM JobPosting j, Company c WHERE " +
            "j.companyCd = c.companyCd AND (" +
            "j.jobPostingBody LIKE CONCAT('%', :search, '%') OR " +
            "j.jobPostingMoney LIKE CONCAT('%', :search, '%') OR " +
            "j.jobPostingSkill LIKE CONCAT('%', :search, '%') OR " +
            "j.jobPostingPosition LIKE CONCAT('%', :search, '%') OR " +
            "c.companyLocation LIKE CONCAT('%', :search, '%') OR " +
            "c.companyName LIKE CONCAT('%', :search, '%') OR " +
            "c.companyNation LIKE CONCAT('%', :search, '%'))")
    List<JobPosting> searchJobPostings(@Param("search") String search);
}
