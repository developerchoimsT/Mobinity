package com.ms.hoopi.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserJoinDto {
    private String id;
    private String pwd;
    private String name;
    private String phone;
    private String email;
    private String address;
    private LocalDate birth;
}

