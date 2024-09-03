package com.ms.hoopi.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "product")
public class Product {
    @Id
    @Column(name = "product_code", nullable = false)
    private String productCode;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "price", nullable = false)
    private Long price;

    @ColumnDefault("0")
    @Column(name = "stock", nullable = false)
    private Long stock;

    @OneToMany(mappedBy = "productCode")
    private Set<Article> articles = new LinkedHashSet<>();

    @OneToMany(mappedBy = "productCode")
    private Set<CartDetail> cartDetails = new LinkedHashSet<>();

    @OneToMany(mappedBy = "productCode")
    private Set<OrderDetail> orderDetails = new LinkedHashSet<>();

    @OneToMany(mappedBy = "productCode")
    private Set<ProductImg> productImgs = new LinkedHashSet<>();

}