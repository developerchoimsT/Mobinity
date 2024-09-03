package com.ms.hoopi.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @Column(name = "payment_code", nullable = false)
    private String paymentCode;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_code", nullable = false)
    private Order orderCode;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "code", nullable = false)
    private User code;

    @Column(name = "method", length = 100)
    private String method;

    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    @Column(name = "bank", length = 150)
    private String bank;

    @Column(name = "payment_amount")
    private Long paymentAmount;

}