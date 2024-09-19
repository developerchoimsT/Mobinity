package com.ms.hoopi.admin.user.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressSelectDto {
    private String addressCode;
    private String address;
    private String main;
}
