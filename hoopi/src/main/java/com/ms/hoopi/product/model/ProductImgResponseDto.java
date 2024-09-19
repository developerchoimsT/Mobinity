package com.ms.hoopi.product.model;

import com.ms.hoopi.model.entity.Product;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ProductImgResponseDto {
    private String productImgCode;
    private Product productCode;
    private String fileName;
    private String imgPath;
    private String imgKey;
    private LocalDateTime createdAt;

}
