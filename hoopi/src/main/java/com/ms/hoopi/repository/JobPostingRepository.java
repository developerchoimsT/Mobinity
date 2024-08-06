package com.ms.hoopi.repository;

import com.ms.hoopi.model.dto.JobPostingDto;
import com.ms.hoopi.model.entity.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPostingRepository extends JpaRepository<JobPosting, Integer> {

}
