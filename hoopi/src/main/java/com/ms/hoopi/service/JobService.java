package com.ms.hoopi.service;

import com.ms.hoopi.model.dto.ApplyDto;
import com.ms.hoopi.model.dto.JobPostingDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface JobService {
    public ResponseEntity<String> insertJob(JobPostingDto jobPosting);

    List getJob(String search);

    Map getJobDetail(String jobPostingCd);

    ResponseEntity<String> applyPostJob(ApplyDto applyDto);

    ResponseEntity<String> putJob(JobPostingDto jobPosting);

    ResponseEntity<String> deleteJob(JobPostingDto jobPosting);
}
