package com.ms.hoopi.admin.article.model;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

@Builder
@Data
public class ProductRequestDto {
    private String name;
    private Long price;
    private Long stock;
}
