package com.ms.hoopi.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Embeddable
public class CartDetailId implements java.io.Serializable {
    private static final long serialVersionUID = -247367939816099055L;
    @Column(name = "product_code", nullable = false)
    private String productCode;

    @Column(name = "cart_code", nullable = false)
    private String cartCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CartDetailId entity = (CartDetailId) o;
        return Objects.equals(this.cartCode, entity.cartCode) &&
                Objects.equals(this.productCode, entity.productCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartCode, productCode);
    }

}