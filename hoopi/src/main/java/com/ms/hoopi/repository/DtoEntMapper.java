package com.ms.hoopi.repository;

import com.ms.hoopi.model.dto.ApplyDto;
import com.ms.hoopi.model.dto.CompanyDto;
import com.ms.hoopi.model.dto.JobPostingDto;
import com.ms.hoopi.model.entity.Apply;
import com.ms.hoopi.model.entity.Company;
import com.ms.hoopi.model.entity.JobPosting;
import com.ms.hoopi.model.entity.Users;
import com.ms.hoopi.model.dto.UsersDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DtoEntMapper {
    @Mapping(target = "usersYn", constant = "Y")
    @Mapping(target = "usersRole", constant = "USER")
    Users toEntity(UsersDto dto);

    UsersDto toDto(Users entity);

    JobPosting toEntity(JobPostingDto dto);
    List<JobPosting> toEntityList(List<JobPostingDto> dtoList);

    @Mapping(source = "company", target = "company")
    JobPostingDto toDto(JobPosting entity);
    @Mapping(source = "company", target = "company")
    List<JobPostingDto> toDtoList(List<JobPosting> entityList);

    Company toEntity(CompanyDto dto);
    List<Company> toCompanyEntityList(List<CompanyDto> dtoList);

    CompanyDto toDto(Company entity);
    List<CompanyDto> toCompanyDtoList(List<Company> entity);

    Apply toEntity(ApplyDto dto);
    ApplyDto toDto(Apply entity);
}