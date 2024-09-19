package com.ms.hoopi.product.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ProductResponseDto {
    private String productCode;
    private String name;
    private Long price;
    private LocalDateTime createdAt;
}
