package com.ms.hoopi.service.serviceImpl;

import com.ms.hoopi.model.dto.ApplyDto;
import com.ms.hoopi.model.dto.CompanyDto;
import com.ms.hoopi.model.dto.JobPostingDto;
import com.ms.hoopi.model.entity.JobPosting;
import com.ms.hoopi.model.entity.Users;
import com.ms.hoopi.repository.*;
import com.ms.hoopi.service.JobService;
import com.ms.hoopi.service.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JobServiceImpl implements JobService {
    private final JobPostingRepository jobPostingRepository;
    private final DtoEntMapper dtoEntMapper;
    private final UserRepository userRepository;
    private final LoginService loginService;
    private final CompanyRepository companyRepository;
    private final ApplyRepository applyRepository;

    public JobServiceImpl(JobPostingRepository jobPostingRepository
                        , DtoEntMapper dtoEntMapper
                        , UserRepository userRepository
                        , LoginService loginService
                        , CompanyRepository companyRepository
                        , ApplyRepository applyRepository) {
        this.jobPostingRepository = jobPostingRepository;
        this.dtoEntMapper = dtoEntMapper;
        this.userRepository = userRepository;
        this.loginService = loginService;
        this.companyRepository = companyRepository;
        this.applyRepository = applyRepository;
    }
    @Override
    public ResponseEntity<String> insertJob(JobPostingDto jobPosting) {
        try{
            String usersId = jobPosting.getCompanyDto().getCompanyCd();
            Users user = userRepository.findByUsersId(usersId);
            if(user == null) {
                return ResponseEntity.ofNullable("등록자를 인식할 수 없습니다.");
            }
            CompanyDto company = jobPosting.getCompanyDto();
            company.setCompanyCd(user.getUsersCd());
            jobPosting.setCompanyDto(company);
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
                System.out.println(jobPostings);
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
            String companyCd = jobPostingDto.getCompanyDto().getCompanyCd();
            companyDto = dtoEntMapper.toDto(companyRepository.findByCompanyCd(companyCd));

            map.put("jobPostingDto", jobPostingDto);
            map.put("companyDto", companyDto);
            return map;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ResponseEntity<String> applyPostJob(ApplyDto applyDto) {
        try{
            applyRepository.save(dtoEntMapper.toEntity(applyDto));
            return ResponseEntity.ok("지원이 완료되었습니다.");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("지원 실패, 다시 시도해주세요");
        }

    }

    @Override
    public ResponseEntity<String> putJob(JobPostingDto jobPosting) {
        try {
            Optional<JobPosting> existingJobPosting = jobPostingRepository.findById(jobPosting.getJobPostingCd());
            if (!existingJobPosting.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 채용공고를 찾을 수 없습니다.");
            }
            JobPostingDto existingJobPostingDto = dtoEntMapper.toDto(existingJobPosting.get());

            // 무조건 수정
            existingJobPostingDto.setJobPostingPosition(jobPosting.getJobPostingPosition());
            existingJobPostingDto.setJobPostingMoney(jobPosting.getJobPostingMoney());
            existingJobPostingDto.setJobPostingBody(jobPosting.getJobPostingBody());
            existingJobPostingDto.setJobPostingSkill(jobPosting.getJobPostingSkill());

            // 수정된 데이터 저장
            jobPostingRepository.save(dtoEntMapper.toEntity(existingJobPostingDto));

            return ResponseEntity.ok("수정되었습니다.");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("잠시 뒤에 다시 시도해주세요.");
        }
    }
}
