package com.ms.hoopi.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "job_posting")
public class JobPosting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_posting_cd", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_cd", nullable = false, referencedColumnName = "company_cd")
    private Company company;

    @Column(name = "job_posting_body", nullable = false)
    private String jobPostingBody;

    @Column(name = "job_posting_money", nullable = false)
    private String jobPostingMoney;

    @Column(name = "job_posting_position", nullable = false)
    private String jobPostingPosition;

    @Column(name = "job_posting_skill", nullable = false)
    private String jobPostingSkill;

}