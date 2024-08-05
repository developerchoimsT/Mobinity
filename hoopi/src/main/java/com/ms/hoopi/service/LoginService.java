package com.ms.hoopi.service;

import com.ms.hoopi.model.dto.UsersDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface LoginService {
    boolean validateUser(HttpServletResponse response, HttpServletRequest request, UsersDto user);
}
