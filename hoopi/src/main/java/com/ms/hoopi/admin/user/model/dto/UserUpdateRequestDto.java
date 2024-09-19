package com.ms.hoopi.admin.user.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdateRequestDto {
    private String id;
    private String name;
    private String email;
    private String phone;
}
