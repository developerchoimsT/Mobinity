package com.ms.hoopi.service;

import com.ms.hoopi.model.dto.JobPostingDto;
import org.springframework.http.ResponseEntity;

public interface JobService {
    public ResponseEntity<String> insertJob(JobPostingDto jobPosting);
}
