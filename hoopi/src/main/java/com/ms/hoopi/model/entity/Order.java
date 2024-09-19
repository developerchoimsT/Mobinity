package com.ms.hoopi.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "`order`")
public class Order {
    @Id
    @Column(name = "order_code", nullable = false)
    private String orderCode;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "code", nullable = false)
    private User code;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @JsonManagedReference
    @OneToMany(mappedBy = "orderCode")
    private Set<Delivery> deliveries = new LinkedHashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "orderCode")
    private Set<OrderDetail> orderDetails = new LinkedHashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "orderCode")
    private Set<Payment> payments = new LinkedHashSet<>();

}