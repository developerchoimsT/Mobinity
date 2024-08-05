package com.ms.hoopi.controller;

import com.ms.hoopi.model.dto.JobPostingDto;
import com.ms.hoopi.service.PostJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("hoopi")
public class PostJobController {
    @Autowired
    private final PostJobService postJobService;

    public PostJobController(PostJobService postJobService) {
        this.postJobService = postJobService;
    }
    @PostMapping("/job")
    public ResponseEntity<String> insertPostJob(@RequestBody JobPostingDto jobPosting) {
        return postJobService.insertJob(jobPosting);
    }
}
