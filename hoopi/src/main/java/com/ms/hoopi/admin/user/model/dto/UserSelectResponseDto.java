package com.ms.hoopi.admin.user.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSelectResponseDto {
    private String code;
    private String userId;
    private String userName;
    private String email;
    private String phone;
}
