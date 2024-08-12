package com.ms.hoopi.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "apply")
public class Apply {
    @Id
    @Column(name = "apply_id", nullable = false)
    private int id;

    @Column(name = "job_posting_cd", nullable = false)
    private int jobPostingCd;

    @Column(name = "users_id", nullable = false)
    private String usersId;

}