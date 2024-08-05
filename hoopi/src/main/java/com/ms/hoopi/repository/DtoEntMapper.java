package com.ms.hoopi.repository;

import com.ms.hoopi.model.dto.JobPostingDto;
import com.ms.hoopi.model.entity.JobPosting;
import com.ms.hoopi.model.entity.Users;
import com.ms.hoopi.model.dto.UsersDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DtoEntMapper {
    @Mapping(target = "usersYn", constant = "Y")
    @Mapping(target = "usersRole", constant = "USER")
    Users toEntity(UsersDto dto);

    UsersDto toDto(Users entity);

    JobPostingDto toEntity(JobPostingDto dto);

    JobPosting toDto(JobPosting entity);
}