package com.ms.hoopi.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "product_img")
public class ProductImg {
    @Id
    @Column(name = "product_img_code", nullable = false)
    private String productImgCode;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_code", nullable = false)
    private Product productCode;

    @ColumnDefault("'N'")
    @Column(name = "product_img_main", length = 1)
    private String productImgMain;

}