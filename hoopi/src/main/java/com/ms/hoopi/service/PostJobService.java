package com.ms.hoopi.service;

import com.ms.hoopi.model.dto.JobPostingDto;
import org.springframework.http.ResponseEntity;

public interface PostJobService {
    public ResponseEntity<String> insertJob(JobPostingDto jobPosting);
}
