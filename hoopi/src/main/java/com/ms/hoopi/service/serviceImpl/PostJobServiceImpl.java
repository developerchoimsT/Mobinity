package com.ms.hoopi.service.serviceImpl;

import com.ms.hoopi.model.dto.JobPostingDto;
import com.ms.hoopi.model.entity.JobPosting;
import com.ms.hoopi.repository.DtoEntMapper;
import com.ms.hoopi.repository.JobPostingRepository;
import com.ms.hoopi.service.PostJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PostJobServiceImpl implements PostJobService {
    @Autowired
    private final JobPostingRepository jobPostingRepository;
    @Autowired
    private final DtoEntMapper dtoEntMapper;

    public PostJobServiceImpl(JobPostingRepository jobPostingRepository, DtoEntMapper dtoEntMapper) {
        this.jobPostingRepository = jobPostingRepository;
        this.dtoEntMapper = dtoEntMapper;
    }
    @Override
    public ResponseEntity<String> insertJob(JobPostingDto jobPosting) {
        try{
            jobPostingRepository.save(jobPosting);
            return ResponseEntity.ok("귀사가 바라는 인재가 지원하기를 기원합니다.");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("잠시 뒤에 다시 시도해주세요.");
        }
    }
}
