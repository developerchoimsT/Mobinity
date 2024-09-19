package com.ms.hoopi.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginResponseDto {
    private String id;
    private String role;
    private String msg;
}
