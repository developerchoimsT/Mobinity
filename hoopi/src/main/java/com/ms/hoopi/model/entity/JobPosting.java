package com.ms.hoopi.model.entity;

import com.ms.hoopi.model.dto.CompanyDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name="jobPosting")
public class JobPosting {
    @Id
    private int jobPostingCd;
    @Column(nullable = false)
    private String jobPostingPosition;
    @Column(nullable = false)
    private String jobPostingMoney;
    @Column(nullable = false)
    private String jobPostingBody;
    @Column(nullable = false)
    private String jobPostingSkill;
    @Column(nullable = false)
    private String companyCd;

}
