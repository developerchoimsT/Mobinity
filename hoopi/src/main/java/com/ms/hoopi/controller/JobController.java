package com.ms.hoopi.controller;

import com.ms.hoopi.model.dto.JobPostingDto;
import com.ms.hoopi.service.JobService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
    @GetMapping("/job")
    public ResponseEntity<List> getJob(@RequestParam String search) {
        List list= Collections.singletonList(jobService.getJob(search));
        return ResponseEntity.ok(list);
    }
    @GetMapping("/jobDetail")
    public ResponseEntity<Map<String, Object>> getJobDetail(@RequestParam String jobPostingCd) {
        Map map = jobService.getJobDetail(jobPostingCd);
        return ResponseEntity.ok(map);
    }
}
