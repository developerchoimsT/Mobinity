package com.ms.hoopi.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "apply")
public class Apply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 자동 증가 설정
    @Column(name = "apply_id", nullable = false)
    private int id;

    @Column(name = "job_posting_cd", nullable = false)
    private int jobPostingCd;

    @Column(name = "users_id", nullable = false)
    private String usersId;

}