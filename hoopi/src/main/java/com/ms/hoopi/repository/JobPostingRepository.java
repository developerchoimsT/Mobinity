package com.ms.hoopi.repository;

import com.ms.hoopi.model.dto.JobPostingDto;
import com.ms.hoopi.model.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPostingRepository extends JpaRepository<Company, Integer> {

    void save(JobPostingDto jobPostingDto);
}
