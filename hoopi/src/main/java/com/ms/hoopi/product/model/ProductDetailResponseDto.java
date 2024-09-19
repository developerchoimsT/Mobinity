package com.ms.hoopi.product.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDetailResponseDto {
    private ProductResponseDto product;
    private String imgUrl;
    private String boardContent;
}
