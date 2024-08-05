package com.ms.hoopi.model.dto;

import lombok.Data;

@Data
public class JobPostingDto {
    private int jobPostingCd;
    private String jobPostingPosition;
    private String jobPostingMoney;
    private String jobPostingBody;
    private String jobPostingSkill;
    private String companyCd;
}
