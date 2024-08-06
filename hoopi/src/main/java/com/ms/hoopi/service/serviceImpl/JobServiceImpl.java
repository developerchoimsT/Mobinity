package com.ms.hoopi.service.serviceImpl;

import com.ms.hoopi.model.dto.CompanyDto;
import com.ms.hoopi.model.dto.JobPostingDto;
import com.ms.hoopi.model.dto.UsersDto;
import com.ms.hoopi.model.entity.Company;
import com.ms.hoopi.model.entity.JobPosting;
import com.ms.hoopi.model.entity.Users;
import com.ms.hoopi.repository.CompanyRepository;
import com.ms.hoopi.repository.DtoEntMapper;
import com.ms.hoopi.repository.JobPostingRepository;
import com.ms.hoopi.repository.UserRepository;
import com.ms.hoopi.service.JobService;
import com.ms.hoopi.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JobServiceImpl implements JobService {
    private final JobPostingRepository jobPostingRepository;
    private final DtoEntMapper dtoEntMapper;
    private final UserRepository userRepository;
    private final LoginService loginService;
    private final CompanyRepository companyRepository;

    public JobServiceImpl(JobPostingRepository jobPostingRepository
                        , DtoEntMapper dtoEntMapper
                        , UserRepository userRepository
                        , LoginService loginService
                        , CompanyRepository companyRepository) {
        this.jobPostingRepository = jobPostingRepository;
        this.dtoEntMapper = dtoEntMapper;
        this.userRepository = userRepository;
        this.loginService = loginService;
        this.companyRepository = companyRepository;
    }
    @Override
    public ResponseEntity<String> insertJob(JobPostingDto jobPosting) {
        try{
            String usersId = jobPosting.getCompanyCd();
            Users user = userRepository.findByUsersId(usersId);
            jobPosting.setCompanyCd(user.getUsersCd());
            jobPostingRepository.save(dtoEntMapper.toEntity(jobPosting));
            return ResponseEntity.ok("귀사가 바라는 인재가 지원하기를 기원합니다.");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("잠시 뒤에 다시 시도해주세요.");
        }
    }

    @Override
    public Map<String, Object> getJob(String search) {

        Map map = new HashMap();
        List<JobPostingDto> jobPostings = new ArrayList();
        List<CompanyDto> companies = new ArrayList();

        try{
            if(search == null || search.isEmpty()){
                jobPostings = dtoEntMapper.toDtoList(jobPostingRepository.findAll());
            } else {
                jobPostings = dtoEntMapper.toDtoList(jobPostingRepository.searchJobPostings(search));
            }
            for (JobPostingDto job : jobPostings) {
                Company company = companyRepository.findByCompanyCd(job.getCompanyCd());
                if (company != null) {
                    companies.add(dtoEntMapper.toDto(company));
                }
            }
            map.put("jobPostings", jobPostings);
            map.put("companies", companies);
            return  map;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
