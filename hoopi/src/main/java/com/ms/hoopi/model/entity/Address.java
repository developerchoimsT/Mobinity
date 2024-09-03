package com.ms.hoopi.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "address")
public class Address {
    @Id
    @Column(name = "address_code", nullable = false)
    private String addressCode;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "code", nullable = false)
    private User code;

    @Column(name = "address", nullable = false, length = 500)
    private String address;

    @ColumnDefault("'Y'")
    @Column(name = "main", nullable = false, length = 1)
    private String main;

    @OneToMany(mappedBy = "addressCode")
    private Set<Delivery> deliveries = new LinkedHashSet<>();

    @Builder
    public Address(String addressCode, User code, String address) {
        this.addressCode = addressCode;
        this.code = code;
        this.address = address;
    }

}