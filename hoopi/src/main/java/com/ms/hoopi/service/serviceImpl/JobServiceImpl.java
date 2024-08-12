package com.ms.hoopi.service.serviceImpl;

import com.ms.hoopi.model.dto.CompanyDto;
import com.ms.hoopi.model.dto.JobPostingDto;
import com.ms.hoopi.model.entity.Users;
import com.ms.hoopi.repository.CompanyRepository;
import com.ms.hoopi.repository.DtoEntMapper;
import com.ms.hoopi.repository.JobPostingRepository;
import com.ms.hoopi.repository.UserRepository;
import com.ms.hoopi.service.JobService;
import com.ms.hoopi.service.LoginService;
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
            if(user == null) {
                return ResponseEntity.ofNullable("등록자를 인식할 수 없습니다.");
            }
            jobPosting.setCompanyCd(user.getUsersCd());
            jobPostingRepository.save(dtoEntMapper.toEntity(jobPosting));
            return ResponseEntity.ok("귀사가 바라는 인재가 지원하기를 기원합니다.");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("잠시 뒤에 다시 시도해주세요.");
        }
    }

    @Override
    public List getJob(String search) {

        List<JobPostingDto> jobPostings = new ArrayList();

        try{
            if(search == null || search.isEmpty()){
                jobPostings = dtoEntMapper.toDtoList(jobPostingRepository.findJobPosting());
            } else {
                jobPostings = dtoEntMapper.toDtoList(jobPostingRepository.searchJobPostings(search));
            }
            return  jobPostings;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Map getJobDetail(String jobPostingCd) {
        Map map = new HashMap();
        JobPostingDto jobPostingDto = new JobPostingDto();
        CompanyDto companyDto = new CompanyDto();

        try{
            int cd = Integer.valueOf(jobPostingCd);
            jobPostingDto = dtoEntMapper.toDto(jobPostingRepository.findJobPostingByJobPostingCd(cd));
            String companyCd = jobPostingDto.getCompanyCd();
            companyDto = dtoEntMapper.toDto(companyRepository.findByCompanyCd(companyCd));

            map.put("jobPostingDto", jobPostingDto);
            map.put("companyDto", companyDto);
            return map;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
