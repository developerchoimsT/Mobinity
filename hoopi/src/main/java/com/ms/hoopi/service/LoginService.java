package com.ms.hoopi.service;

import com.ms.hoopi.model.dto.UsersDto;

public interface LoginService {
    boolean validateUser(UsersDto user);
}
