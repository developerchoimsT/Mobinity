package com.ms.hoopi.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "delivery")
public class Delivery {
    @Id
    @Column(name = "delivery_code", nullable = false)
    private String deliveryCode;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_code", nullable = false)
    private Order orderCode;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "code", nullable = false)
    private User code;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "address_code", nullable = false)
    private Address addressCode;

    @Column(name = "tracking_number", nullable = false, length = 100)
    private String trackingNumber;

    @Column(name = "courier", nullable = false, length = 100)
    private String courier;

}