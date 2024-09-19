package com.ms.hoopi.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class OrderDetailId implements java.io.Serializable {
    private static final long serialVersionUID = -3410358882762092828L;
    @Column(name = "product_code", nullable = false)
    private String productCode;

    @Column(name = "order_code", nullable = false)
    private String orderCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OrderDetailId entity = (OrderDetailId) o;
        return Objects.equals(this.productCode, entity.productCode) &&
                Objects.equals(this.orderCode, entity.orderCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productCode, orderCode);
    }

}