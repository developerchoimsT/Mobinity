package com.ms.hoopi.model.entity;

import com.ms.hoopi.model.dto.CompanyDto;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name="job_posting")
public class JobPosting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
