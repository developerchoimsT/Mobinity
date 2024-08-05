package com.ms.hoopi.model.entity;

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
@Table(name="company")
public class Company {
    @Id
    private String companyCd;
    @Column(nullable=false)
    private String companyName;
    @Column(nullable=false)
    private String companyNation;
    @Column(nullable=false)
    private String companyLocation;
}
