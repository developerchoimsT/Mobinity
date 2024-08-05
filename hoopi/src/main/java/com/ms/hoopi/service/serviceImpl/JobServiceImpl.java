package com.ms.hoopi.service.serviceImpl;

import com.ms.hoopi.model.dto.JobPostingDto;
import com.ms.hoopi.model.entity.Users;
import com.ms.hoopi.repository.DtoEntMapper;
import com.ms.hoopi.repository.JobPostingRepository;
import com.ms.hoopi.repository.UserRepository;
import com.ms.hoopi.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class JobServiceImpl implements JobService {
    @Autowired
    private final JobPostingRepository jobPostingRepository;
    @Autowired
    private final DtoEntMapper dtoEntMapper;
    @Autowired
    private final UserRepository userRepository;

    private UUID uuid;

    public JobServiceImpl(JobPostingRepository jobPostingRepository, DtoEntMapper dtoEntMapper, UserRepository userRepository) {
        this.jobPostingRepository = jobPostingRepository;
        this.dtoEntMapper = dtoEntMapper;
        this.userRepository = userRepository;
    }
    @Override
    public ResponseEntity<String> insertJob(JobPostingDto jobPosting) {
        try{
            String usersId = jobPosting.getCompanyCd();
            Users user = userRepository.findByUsersId(usersId);
            jobPosting.setCompanyCd(user.getUsersCd());
            jobPostingRepository.save(jobPosting);
            return ResponseEntity.ok("귀사가 바라는 인재가 지원하기를 기원합니다.");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("잠시 뒤에 다시 시도해주세요.");
        }
    }
}
