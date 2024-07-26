package com.ms.hoopi;

import com.ms.hoopi.entity.Users;
import com.ms.hoopi.join.UsersDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DtoEntMapper {
    @Mapping(target = "usersYn", constant = "Y")
    @Mapping(target = "usersRole", constant = "USER")
    Users toEntity(UsersDto dto);

    UsersDto toDto(Users entity);
}