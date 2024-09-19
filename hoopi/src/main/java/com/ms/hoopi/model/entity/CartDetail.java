package com.ms.hoopi.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "cart_detail")
public class CartDetail {
    @EmbeddedId
    private CartDetailId id;

    @MapsId("productCode")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_code", nullable = false)
    private Product productCode;

    @MapsId("cartCode")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cart_code", nullable = false)
    private Cart cartCode;

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @Column(name = "cart_amount", nullable = false)
    private Long cartAmount;

}