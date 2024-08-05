package com.ms.hoopi.controller;

import com.ms.hoopi.model.dto.JobPostingDto;
import com.ms.hoopi.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hoopi")
public class JobController {
    @Autowired
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }
    @PostMapping("/job")
    public ResponseEntity<String> insertPostJob(@RequestBody JobPostingDto jobPosting) {
        return jobService.insertJob(jobPosting);
    }
}
