package com.ms.hoopi.admin.user.service;

import com.ms.hoopi.admin.user.model.dto.UserDetailSelectResponseDto;
import com.ms.hoopi.admin.user.model.dto.UserOrderSelectDto;
import com.ms.hoopi.admin.user.model.dto.UserSelectResponseDto;
import com.ms.hoopi.admin.user.model.dto.UserUpdateRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface AdminUserService {
    Page<UserSelectResponseDto> userSelect(String searchCate, String keyword, int page, int size);

    ResponseEntity<?> userSelectDetail(String code);

    Page<UserOrderSelectDto> userOrder(String code, int page, int size);

    ResponseEntity<String> userQuit(String id);

    ResponseEntity<String> userUpdate(UserUpdateRequestDto user);
}

