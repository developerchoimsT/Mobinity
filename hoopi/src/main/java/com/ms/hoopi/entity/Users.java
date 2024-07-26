package com.ms.hoopi.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name="users")
public class Users {
    @Id
    private String usersCd;
    @Column(nullable = false)
    private String usersNm;
    @Column(nullable = false)
    private String usersId;
    @Column(nullable = false)
    private String usersPw;
    @Column(nullable = false)
    private String usersEmail;
    @Column(nullable = false)
    private String usersPhone;
    @Column(nullable = false)
    private String usersRole;
    @Column(nullable = false)
    private String usersYn;
}
